package ca.sperrer.p0t4t0sandwich.tatercomms.common.player;

import java.util.UUID;

public interface TaterPlayer {
    UUID getUUID();

    String getName();

    String getDisplayName();

    void sendMessage(String message);
}
