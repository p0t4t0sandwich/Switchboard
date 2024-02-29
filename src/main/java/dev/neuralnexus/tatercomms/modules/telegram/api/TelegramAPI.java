package dev.neuralnexus.tatercomms.modules.telegram.api;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import dev.neuralnexus.tatercomms.TaterComms;
import dev.neuralnexus.tatercomms.api.message.Message;
import dev.neuralnexus.tatercomms.config.TaterCommsConfigLoader;
import dev.neuralnexus.tatercomms.config.sections.telegram.ChatChannel;
import dev.neuralnexus.tatercomms.config.sections.telegram.ChatMapping;
import dev.neuralnexus.tatercomms.event.ReceiveMessageEvent;
import dev.neuralnexus.tatercomms.event.api.TaterCommsEvents;
import dev.neuralnexus.taterlib.placeholder.PlaceholderParser;

import java.util.List;
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
                    this::onMessageReceived,
                    // Create Exception Handler
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

        public int onMessageReceived(List<Update> updates) {
            updates.forEach(
                    update -> {
                        if (update.message().from().isBot()) {
                            return;
                        }
                        if (update.message() == null) {
                            return;
                        }

                        // Get the message
                        com.pengrad.telegrambot.model.Message message = update.message();
                        String content = message.text();

                        if (content == null) {
                            return;
                        }

                        // Get the channelId and Title
                        long channelId = update.message().chat().id();
                        String title = update.message().chat().title();

                        // Check if the channel is a server channel
                        Optional<String> server =
                                TaterCommsConfigLoader.config()
                                        .telegram()
                                        .getServerName(channelId, title);
                        if (!server.isPresent()) {
                            return;
                        }

                        // Send the message
                        TaterCommsEvents.RECEIVE_MESSAGE.invoke(
                                new ReceiveMessageEvent(
                                        new Message(
                                                new TelegramPlayer(update.message()),
                                                Message.MessageType.PLAYER_MESSAGE,
                                                content,
                                                TaterCommsConfigLoader.config()
                                                        .formatting()
                                                        .telegram())));
                    });

            TaterCommsConfigLoader.save();
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }

        public void sendMessage(Message message) {
            String messageContent = PlaceholderParser.stripSectionSign(message.applyPlaceHolders());
            if (message.sender().server() == null) {
                return;
            }
            String server = message.sender().server().name();

            // Get the channel
            Optional<ChatMapping> mappings =
                    TaterCommsConfigLoader.config().telegram().getMappings(server);
            if (!mappings.isPresent()) {
                return;
            }

            // Send the message
            for (ChatChannel channel : mappings.get().channels()) {
                bot.execute(new SendMessage(channel.chatId(), messageContent));
            }
        }
    }
}
