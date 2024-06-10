/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.switchboard.api.rework.sender;

import net.dv8tion.jda.api.entities.User;

public class DiscordSender implements Sender {
    private final User user;
    private final String name;

    public DiscordSender(User user) {
        this.user = user;
        this.name = this.user.getName();
    }

    @Override
    public String name() {
        return this.name;
    }

    @Override
    public void send(String message) {
        user.openPrivateChannel().queue(channel -> channel.sendMessage(message).queue());
    }
}
