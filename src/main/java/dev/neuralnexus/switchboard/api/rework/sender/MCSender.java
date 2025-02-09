/**
 * Copyright (c) 2025 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">MIT</a>
 */

package dev.neuralnexus.switchboard.api.rework.sender;

import dev.neuralnexus.taterapi.entity.player.User;

public class MCSender implements Sender {
    private final User player;

    public MCSender(User player) {
        this.player = player;
    }

    @Override
    public String name() {
        return player.name();
    }

    @Override
    public void send(String message) {
        player.sendMessage(message);
    }
}
