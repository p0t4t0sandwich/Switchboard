package dev.neuralnexus.tatercomms.modules.telegram.api;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;

import dev.neuralnexus.tatercomms.TaterComms;
import dev.neuralnexus.tatercomms.api.message.Message;
import dev.neuralnexus.tatercomms.config.TaterCommsConfigLoader;
import dev.neuralnexus.tatercomms.config.sections.telegram.ChatMapping;
import dev.neuralnexus.taterlib.placeholder.PlaceholderParser;

import java.util.Optional;

/** API for the Telegram module. */
public class TelegramAPI {
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
     * Send a message to a Telegram channel
     *
     * @param message The message
     */
    public void sendMessage(Message message) {
        if (bot != null) {
            bot.sendMessage(message);
        }
    }

    public class Bot {
        TelegramBot bot;

        public Bot() {
            bot = new TelegramBot(TaterCommsConfigLoader.config().telegram().token());

            // Register for updates
            bot.setUpdatesListener(
                    updates -> {
                        // ... process updates
                        // return id of last processed update or confirm them all

                        updates.forEach(
                                update -> {
                                    if (update.message() != null) {
                                        String message = update.message().text();
                                        if (message != null) {
                                            System.out.println(update.message().chat().title());
                                            System.out.println(message);
                                        }
                                    }
                                });

                        return UpdatesListener.CONFIRMED_UPDATES_ALL;
                        // Create Exception Handler
                    },
                    e -> {
                        if (e.response() != null) {
                            // got bad response from telegram
                            e.response().errorCode();
                            e.response().description();
                        } else {
                            // probably network error
                            e.printStackTrace();
                        }
                    });

            TaterComms.logger().info("Telegram bot is ready!");
        }

        public void removeListeners() {
            bot.removeGetUpdatesListener();
        }

        public void sendMessage(Message message) {
            String messageContent = PlaceholderParser.stripSectionSign(message.applyPlaceHolders());
            if (message.getSender().server() == null) {
                return;
            }
            String server = message.getSender().server().name();

            // Get the channel
            Optional<ChatMapping> mappings =
                    TaterCommsConfigLoader.config().telegram().getMappings(server);
            if (!mappings.isPresent()) {
                return;
            }

            for (String channel : mappings.get().channels()) {
                //            // Get the guild and channel
                //            Guild guild = api.getGuildById(channel.guildId());
                //            if (guild == null) {
                //                TaterComms.logger()
                //                        .error(
                //                                "Guild not found for server "
                //                                        + server
                //                                        + ", please check the config!");
                //                return;
                //            }
                //            TextChannel textChannel =
                // guild.getTextChannelById(channel.channelId());
                //            if (textChannel == null) {
                //                TaterComms.logger()
                //                        .error(
                //                                "Channel not found for server "
                //                                        + server
                //                                        + ", please check the config!");
                //                return;
                //            }
                //
                //            // Send the message
                //            textChannel.sendMessage(messageContent).queue();
            }
        }
    }
}
