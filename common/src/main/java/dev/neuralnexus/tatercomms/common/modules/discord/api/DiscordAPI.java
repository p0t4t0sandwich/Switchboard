package dev.neuralnexus.tatercomms.common.modules.discord.api;

import dev.neuralnexus.tatercomms.common.TaterComms;
import dev.neuralnexus.tatercomms.common.TaterCommsConfig;
import dev.neuralnexus.tatercomms.common.discord.player.DiscordPlayer;
import dev.neuralnexus.tatercomms.common.event.ReceiveMessageEvent;
import dev.neuralnexus.tatercomms.common.event.api.Message;
import dev.neuralnexus.tatercomms.common.event.api.TaterCommsEvents;
import dev.neuralnexus.taterlib.common.placeholder.PlaceholderParser;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.HashMap;

/**
 * API for the Discord module.
 */
public class DiscordAPI {
    private static Bot bot = null;

    /**
     * Start the bot.
     */
    public void startBot() {
        if (bot == null) {
            bot = new Bot();
        }
    }

    /**
     * Remove the bot.
     */
    public void removeBot() {
        if (bot != null) {
            bot.removeListeners();
        }
        bot = null;
    }

    /**
     * Send a message to a Discord channel
     * @param message The message
     */
    public void sendMessage(Message message) {
        if (bot != null) {
            bot.sendMessage(message);
        }
    }

    static class Bot extends ListenerAdapter {
        private JDA api;

        Bot() {
            try {
                // Create the JDA instance
                api = JDABuilder.createDefault(TaterCommsConfig.DiscordConfig.token()).enableIntents(GatewayIntent.MESSAGE_CONTENT).build();

                // Add the listener
                api.addEventListener(this);
            } catch (Exception e) {
                TaterComms.getLogger().info("Failed to start Discord Bot!");
                e.printStackTrace();
            }
        }

        /**
         * Remove event listeners.
         */
        public void removeListeners() {
            api.removeEventListener(this);
        }

        /**
         * @inheritDoc
         */
        @Override
        public void onReady(ReadyEvent event) {
            TaterComms.getLogger().info("Discord bot is ready!");
        }

        /**
         * Converts the Discord message to a Message object, then invokes the ReceiveMessageEvent.
         * @param event The event
         */
        @Override
        public void onMessageReceived(MessageReceivedEvent event) {
            if (event.getAuthor().isBot()) return;

            // Get the message
            net.dv8tion.jda.api.entities.Message message = event.getMessage();
            String content = message.getContentRaw();

            // Get the guild and channel
            String guildID = message.getGuild().getId();
            String channelID = message.getChannel().getId();

            String server = null;
            for (String key : TaterCommsConfig.DiscordConfig.channels().keySet()) {
                if (TaterCommsConfig.DiscordConfig.channels().get(key).equals(guildID + "/" + channelID)) {
                    server = key;
                    break;
                }
            }

            // Check if the channel is a server channel
            if (server == null) {
                return;
            }

            // Get the author
            User author = message.getAuthor();
            DiscordPlayer discordPlayer = new DiscordPlayer(author);

            // Send the message
            HashMap<String, String> placeholders = new HashMap<>();
            placeholders.put("message", content);
            TaterCommsEvents.RECEIVE_MESSAGE.invoke(new ReceiveMessageEvent(new Message(
                    discordPlayer,
                    Message.MessageType.PLAYER_MESSAGE,
                    content,
                    TaterCommsConfig.formattingChat().get("discord"),
                    placeholders
            )));
        }

        /**
         * Send a message to a Discord channel
         * @param message The message
         */
        public void sendMessage(Message message) {
            String messageContent = PlaceholderParser.stripSectionSign(message.applyPlaceHolders());
            String server = message.getSender().getServerName();

            // Get the channel
            String channelGuildId = TaterCommsConfig.DiscordConfig.channels().get(server);
            if (channelGuildId == null) {
                return;
            }

            // Get the guild and channel
            Guild guild = api.getGuildById(channelGuildId.split("/")[0]);
            if (guild == null) {
                System.err.println("Guild not found for server " + server + ", please check the config!");
                return;
            }
            TextChannel channel = guild.getTextChannelById(channelGuildId.split("/")[1]);
            if (channel == null) {
                System.err.println("Channel not found for server " + server + ", please check the config!");
                return;
            }

            // Send the message
            channel.sendMessage(messageContent).queue();
        }
    }
}
