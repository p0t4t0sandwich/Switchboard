/**
 * Copyright (c) 2025 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">MIT</a>
 */

package dev.neuralnexus.switchboard.modules.telegram.api;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.User;

import dev.neuralnexus.taterapi.server.SimpleServer;

import java.util.UUID;

/** Telegram player. */
public class TelegramPlayer implements dev.neuralnexus.taterapi.entity.player.User {
    private final User user;
    private final String name;
    private final String displayName;
    private final TelegramServer server;

    /**
     * Constructor.
     *
     * @param message The message
     */
    public TelegramPlayer(Message message) {
        this.user = message.from();
        this.name = this.user.username();
        this.displayName = this.user.firstName();
        this.server = new TelegramServer(message);
    }

    @Override
    public String displayName() {
        return this.displayName;
    }

    @Override
    public SimpleServer server() {
        return this.server;
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public void sendMessage(String s) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public UUID uuid() {
        return UUID.randomUUID();
    }

    @Override
    public String prefix() {
        return "";
    }

    @Override
    public String suffix() {
        return "";
    }
}
