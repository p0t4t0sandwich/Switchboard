/**
 * Copyright (c) 2025 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">MIT</a>
 */

package dev.neuralnexus.switchboard.api.impl.webhook;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.WebhookClientBuilder;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import club.minnced.discord.webhook.send.WebhookMessage;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;

import dev.neuralnexus.switchboard.api.Packet;
import dev.neuralnexus.switchboard.config.SwitchboardConfigLoader;

/** API for the Webhook module. */
public class WebhookAPI {
    /**
     * Send a message to a Webhook/GET/POST endpoint
     *
     * @param packet The message
     */
    public void send(Packet packet) {
        String messageContent = packet.content();
        WebhookClientBuilder builder =
                new WebhookClientBuilder(SwitchboardConfigLoader.config().webhook().url());
        builder.setThreadFactory(
                        (job) -> {
                            Thread thread = new Thread(job);
                            thread.setName("Switchboard Webhook Thread");
                            thread.setDaemon(true);
                            return thread;
                        })
                .setWait(true);
        WebhookClient client = builder.build();
        WebhookEmbed embed =
                new WebhookEmbedBuilder()
                        .setTitle(new WebhookEmbed.EmbedTitle(packet.sender(), null))
                        .setDescription(messageContent)
                        .setColor(0x00ff00)
                        .build();
        WebhookMessage message =
                new WebhookMessageBuilder().setUsername("Minecraft").addEmbeds(embed).build();
        client.send(message);
    }
}
