/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.switchboard.api.rework.message;

import dev.neuralnexus.switchboard.api.rework.origin.MCOrigin;
import dev.neuralnexus.switchboard.api.rework.origin.Origin;
import dev.neuralnexus.switchboard.api.rework.sender.MCSender;
import dev.neuralnexus.switchboard.api.rework.sender.Sender;
import dev.neuralnexus.taterlib.event.player.PlayerMessageEvent;

import java.util.UUID;

public class MCMessage implements Message {
    private final PlayerMessageEvent event;

    public MCMessage(PlayerMessageEvent event) {
        this.event = event;
    }

    @Override
    public Origin origin() {
        return new MCOrigin(event.player().server());
    }

    @Override
    public Sender sender() {
        return new MCSender(event.player());
    }

    @Override
    public UUID message_id() {
        return UUID.randomUUID();
    }

    @Override
    public String message_type() {
        return "sb:p_m";
    }

    @Override
    public String content() {
        return event.message();
    }
}
