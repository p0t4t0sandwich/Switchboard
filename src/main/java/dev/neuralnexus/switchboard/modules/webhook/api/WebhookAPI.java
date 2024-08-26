/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.switchboard.modules.webhook.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import dev.neuralnexus.switchboard.Switchboard;
import dev.neuralnexus.switchboard.api.message.Message;
import dev.neuralnexus.switchboard.config.SwitchboardConfigLoader;
import dev.neuralnexus.switchboard.config.sections.webhook.WebhookConfig;
import dev.neuralnexus.taterapi.placeholder.PlaceholderParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/** API for the Webhook module. */
public class WebhookAPI {
    private static final Gson gson = new GsonBuilder().create();

    /**
     * Send a message to a Webhook/GET/POST endpoint
     *
     * @param message The message
     */
    public void sendMessage(Message message) {
        if (message.channel() != Message.MessageType.PLAYER_MESSAGE
                && message.channel() != Message.MessageType.PLAYER_LOGIN
                && message.channel() != Message.MessageType.PLAYER_LOGOUT) {
            return;
        }
        String messageContent = PlaceholderParser.stripSectionSign(message.applyPlaceHolders());
        DiscordEmbed embed = new DiscordEmbed(message.sender().name(), messageContent);
        DiscordWebhookPayload payload =
                new DiscordWebhookPayload("Minecraft", new DiscordEmbed[] {embed});

        String data_json = gson.toJson(payload);

        WebhookConfig config = SwitchboardConfigLoader.config().webhook();
        try {
            URL url = new URL(config.url());

            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setRequestMethod(config.method().toUpperCase());
            con.setRequestProperty("Accept", "application/json");
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("User-Agent", "Switchboard Minecraft Mod");
            OutputStreamWriter osw = new OutputStreamWriter(con.getOutputStream());
            osw.write(data_json);
            osw.flush();
            osw.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

            gson.fromJson(br.readLine(), Map.class);

        } catch (IOException e) {
            Switchboard.logger().error("Could not send webhook", e);
        }
    }

    public static class DiscordWebhookPayload {
        private final String username;
        private final String avatarUrl;
        private final String content;
        private final DiscordEmbed[] embeds;
        private final String[] attachments;

        public DiscordWebhookPayload(
                String username,
                String avatarUrl,
                String content,
                DiscordEmbed[] embeds,
                String[] attachments) {
            this.username = username;
            this.avatarUrl = avatarUrl;
            this.content = content;
            this.embeds = embeds;
            this.attachments = attachments;
        }

        public DiscordWebhookPayload(String username, DiscordEmbed[] embeds) {
            this.username = username;
            this.avatarUrl = "";
            this.content = null;
            this.embeds = embeds;
            this.attachments = new String[] {};
        }

        public Map<Object, Object> toMap() {
            Map<Object, Object> data = new HashMap<>();
            data.put("username", username);
            if (!avatarUrl.isEmpty()) {
                data.put("avatar_url", avatarUrl);
            }
            data.put("content", content);
            Map<Object, Object>[] embedData = new HashMap[embeds.length];
            for (int i = 0; i < embeds.length; i++) {
                embedData[i] = embeds[i].toMap();
            }
            data.put("embeds", embedData);
            data.put("attachments", attachments);
            return data;
        }
    }

    public static class DiscordEmbed {
        private final String title;
        private final String description;
        private final int color;

        public Map<Object, Object> toMap() {
            Map<Object, Object> embedMap = new HashMap<>();
            embedMap.put("title", title);
            embedMap.put("description", description);
            embedMap.put("color", color);
            return embedMap;
        }

        public DiscordEmbed(String title, String description, int color) {
            this.title = title;
            this.description = description;
            this.color = color;
        }

        public DiscordEmbed(String title, String description) {
            this.title = title;
            this.description = description;
            this.color = 0;
        }
    }
}
