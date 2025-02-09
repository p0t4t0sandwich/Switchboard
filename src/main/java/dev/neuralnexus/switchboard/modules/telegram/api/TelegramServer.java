/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.switchboard.modules.telegram.api;

import com.pengrad.telegrambot.model.Message;

import dev.neuralnexus.taterapi.entity.player.User;
import dev.neuralnexus.taterapi.server.SimpleServer;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class TelegramServer implements SimpleServer {
    private final String name;

    public TelegramServer(Message message) {
        this.name = message.chat().title();
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public String brand() {
        return "Telegram";
    }

    @Override
    public List<User> onlinePlayers() {
        return Collections.emptyList();
    }

    @Override
    public Map<String, UUID> playercache() {
        return Collections.emptyMap();
    }

    @Override
    public Map<String, UUID> whitelist() {
        return Collections.emptyMap();
    }
}
