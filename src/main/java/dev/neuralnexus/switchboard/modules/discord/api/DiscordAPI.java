package dev.neuralnexus.switchboard.modules.discord.api;

import dev.neuralnexus.switchboard.Switchboard;
import dev.neuralnexus.switchboard.api.message.Message;
import dev.neuralnexus.switchboard.config.SwitchboardConfigLoader;
import dev.neuralnexus.switchboard.config.sections.discord.ChannelMapping;
import dev.neuralnexus.switchboard.event.ReceiveMessageEvent;
import dev.neuralnexus.switchboard.event.api.SwitchboardEvents;
import dev.neuralnexus.taterlib.placeholder.PlaceholderParser;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.*;

/** API for the Discord module. */
public class DiscordAPI {
    private Bot bot = null;

    /** Start the bot. */
    public void startBot() {
        if (bot == null) {
            bot = new Bot();
        }
    }

    /** Remove the bot. */
    public void removeBot() {
        if (bot != null) {
            bot.removeListeners();
        }
        bot = null;
    }

    /**
     * Send a message to a Discord channel
     *
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
                api =
                        JDABuilder.createDefault(SwitchboardConfigLoader.config().discord().token())
                                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                                .build();

                // Add the listener
                api.addEventListener(this);
            } catch (Exception e) {
                Switchboard.logger().info("Failed to start Discord Bot!");
                e.printStackTrace();
            }
        }

        /** Remove event listeners. */
        public void removeListeners() {
            api.removeEventListener(this);
        }

        @Override
        public void onReady(ReadyEvent event) {
            Switchboard.logger().info("Discord bot is ready!");
        }

        /**
         * Converts the Discord message to a Message object, then invokes the ReceiveMessageEvent.
         *
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

            // Check if the channel is a server channel
            Optional<String> server =
                    SwitchboardConfigLoader.config().discord().findServer(guildID, channelID);
            if (!server.isPresent()) {
                return;
            }

            // Send the message
            SwitchboardEvents.RECEIVE_MESSAGE.invoke(
                    new ReceiveMessageEvent(
                            new Message(
                                    new DiscordPlayer(message),
                                    Message.MessageType.PLAYER_MESSAGE,
                                    content,
                                    SwitchboardConfigLoader.config().formatting().discord())));
        }

        /**
         * Send a message to a Discord channel
         *
         * @param message The message
         */
        public void sendMessage(Message message) {
            String messageContent = PlaceholderParser.stripSectionSign(message.applyPlaceHolders());
            if (message.sender().server() == null) {
                return;
            }
            String server = message.sender().server().name();

            // Get the channel
            Optional<ChannelMapping> mappings =
                    SwitchboardConfigLoader.config().discord().getMappings(server);
            if (!mappings.isPresent()) {
                return;
            }

            for (ChannelMapping.DiscordChannel channel : mappings.get().channels()) {
                // Get the guild and channel
                Guild guild = api.getGuildById(channel.guildId());
                if (guild == null) {
                    Switchboard.logger()
                            .error(
                                    "Guild not found for server "
                                            + server
                                            + ", please check the config!");
                    return;
                }
                TextChannel textChannel = guild.getTextChannelById(channel.channelId());
                if (textChannel == null) {
                    Switchboard.logger()
                            .error(
                                    "Channel not found for server "
                                            + server
                                            + ", please check the config!");
                    return;
                }

                // Send the message
                textChannel.sendMessage(messageContent).queue();
            }
        }
    }
}
