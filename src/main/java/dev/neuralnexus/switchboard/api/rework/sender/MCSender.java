/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.switchboard.api.rework.sender;

import dev.neuralnexus.taterlib.player.SimplePlayer;

public class MCSender implements Sender {
    private final SimplePlayer player;

    public MCSender(SimplePlayer player) {
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
