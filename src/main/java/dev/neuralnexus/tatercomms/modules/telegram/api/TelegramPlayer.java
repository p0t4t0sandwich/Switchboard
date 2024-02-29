package dev.neuralnexus.tatercomms.modules.telegram.api;

import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.User;

import dev.neuralnexus.taterlib.player.SimplePlayer;
import dev.neuralnexus.taterlib.server.SimpleServer;

import java.util.UUID;

/** Telegram player. */
public class TelegramPlayer implements SimplePlayer {
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
    public boolean hasPermission(int i) {
        return false;
    }

    @Override
    public String ipAddress() {
        return "";
    }

    @Override
    public int ping() {
        return 0;
    }

    @Override
    public void kick(String s) {}

    @Override
    public void sendPluginMessage(String s, byte[] bytes) {}

    @Override
    public String prefix() {
        return "";
    }

    @Override
    public String suffix() {
        return "";
    }
}
