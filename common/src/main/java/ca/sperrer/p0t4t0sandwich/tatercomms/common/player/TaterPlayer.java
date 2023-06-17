package ca.sperrer.p0t4t0sandwich.tatercomms.common.player;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.hooks.LuckPermsHook;

import java.util.UUID;

public interface TaterPlayer {
    UUID getUUID();

    String getName();

    String getDisplayName();

    String getServerName();

    void setServerName(String serverName);

    void sendMessage(String message);

    default String getPrefix() {
        LuckPermsHook luckPermsHook = LuckPermsHook.getInstance();
        return luckPermsHook != null ? luckPermsHook.getPrefix(this) : "";
    }

    default String getSuffix() {
        LuckPermsHook luckPermsHook = LuckPermsHook.getInstance();
        return luckPermsHook != null ? luckPermsHook.getSuffix(this) : "";
    }
}
