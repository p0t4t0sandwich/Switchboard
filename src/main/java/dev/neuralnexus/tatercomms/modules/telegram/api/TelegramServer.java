package dev.neuralnexus.tatercomms.modules.telegram.api;

import com.pengrad.telegrambot.model.Message;

import dev.neuralnexus.taterlib.player.SimplePlayer;
import dev.neuralnexus.taterlib.server.SimpleServer;

import java.util.Collections;
import java.util.List;

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
    public List<SimplePlayer> onlinePlayers() {
        return Collections.emptyList();
    }
}
