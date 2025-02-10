/**
 * Copyright (c) 2025 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">MIT</a>
 */

package dev.neuralnexus.switchboard.api.impl.telegram;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;

import dev.neuralnexus.switchboard.Switchboard;
import dev.neuralnexus.switchboard.api.Message;
import dev.neuralnexus.switchboard.api.MessageTypes;
import dev.neuralnexus.switchboard.api.Packet;
import dev.neuralnexus.switchboard.config.SwitchboardConfigLoader;

import java.util.List;

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

    public static class Bot {
        TelegramBot bot;

        public Bot() {
            bot = new TelegramBot(SwitchboardConfigLoader.config().telegram().token());
            bot.setUpdatesListener(
                    this::onMessageReceived,
                    e -> {
                        if (e.response() != null) {
                            e.response().errorCode();
                            e.response().description();
                        } else {
                            e.printStackTrace();
                        }
                    });
            Switchboard.logger().info("Telegram bot is ready!");
        }

        public void removeListeners() {
            bot.removeGetUpdatesListener();
            bot.shutdown();
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

                        // Publish the message
                        Switchboard.bus()
                                .publish(
                                        new Message(
                                                channelId + "/" + title,
                                                update.message().from().firstName(),
                                                MessageTypes.MESSAGE,
                                                content));
                    });

            SwitchboardConfigLoader.save();
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }

        public void send(Packet packet) {
            String[] destIds = packet.sink().split("/");
            if (destIds.length != 2) {
                throw new IllegalArgumentException(
                        "Invalid sink, "
                                + packet.sink()
                                + ". expected format: <channelId>/<title>");
            }
            String channelString = destIds[0];
            long channelId;
            try {
                channelId = Long.parseLong(channelString);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid channel id: " + channelString);
            }
            bot.execute(new SendMessage(channelId, packet.content()));
        }
    }
}
