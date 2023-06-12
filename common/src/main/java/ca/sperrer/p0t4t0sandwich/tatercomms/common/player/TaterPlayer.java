package ca.sperrer.p0t4t0sandwich.tatercomms.common.player;

import java.util.UUID;

public interface TaterPlayer {
    UUID getUUID();

    String getName();

    String getDisplayName();

    String getServerName();

    void setServerName(String serverName);

    void sendMessage(String message);
}
