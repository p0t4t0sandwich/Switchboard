package dev.neuralnexus.tatercomms.common.listeners.player;

import dev.neuralnexus.tatercomms.common.relay.CommsRelay;
import dev.neuralnexus.taterlib.common.TaterLib;
import dev.neuralnexus.taterlib.common.abstractions.player.AbstractPlayer;
import dev.neuralnexus.taterlib.common.relay.MessageRelay;

public interface CommonPlayerListener {
    /**
     * Called when a player makes an advancement, and sends it to the message relay.
     * @param args The player and advancement.
     */
    static void onPlayerAdvancementFinished(Object[] args) {
        AbstractPlayer player = (AbstractPlayer) args[0];
        String advancement = (String) args[1];

        MessageRelay relay = TaterLib.getMessageRelay();
        // Send advancement through relay
        relay.sendSystemMessage(player.getServerName(), player.getDisplayName() + " has made the advancement [" + advancement + "]");
    }

    /**
     * Called when a player dies, and sends it to the message relay.
     * @param args The player and death message.
     */
    static void onPlayerDeath(Object[] args) {
        AbstractPlayer player = (AbstractPlayer) args[0];
        String deathMessage = (String) args[1];

        MessageRelay relay = TaterLib.getMessageRelay();
        // Send death message through relay
        relay.sendSystemMessage(player.getServerName(), deathMessage);
    }

    /**
     * Called when a player logs in, and sends it to the message relay.
     * @param args The player.
     */
    static void onPlayerLogin(Object[] args) {
        AbstractPlayer player = (AbstractPlayer) args[0];

        MessageRelay relay = TaterLib.getMessageRelay();
        // Relay the login message
        relay.sendSystemMessage(player.getServerName(), player.getDisplayName() + " joined the game");
    }

    /**
     * Called when a player logs out, and sends it to the message relay.
     * @param args The player.
     */
    static void onPlayerLogout(Object[] args) {
        AbstractPlayer player = (AbstractPlayer) args[0];

        MessageRelay relay = TaterLib.getMessageRelay();
        // Relay the logout message
        relay.sendSystemMessage(player.getServerName(), player.getDisplayName() + " left the game");
    }

    /**
     * Called when a player sends a message, and sends it to the message relay.
     * @param args The player, message, and whether the message was cancelled.
     */
    static void onPlayerMessage(Object[] args) {
        AbstractPlayer player = (AbstractPlayer) args[0];
        String message = (String) args[1];
        boolean isCancelled = (boolean) args[2];

        MessageRelay relay = TaterLib.getMessageRelay();
        // Send message through relay
        relay.sendPlayerMessage(player, player.getServerName(), message, isCancelled);
    }

    /**
     * Called when a player logs out, and sends it to the message relay.
     * @param args The player and the server they switched from.
     */
    static void onServerSwitch(Object[] args) {
        AbstractPlayer player = (AbstractPlayer) args[0];
        String fromServer = (String) args[1];

        CommsRelay relay = (CommsRelay) TaterLib.getMessageRelay();

        // Get toServer
        String toServer = player.getServerName();

        // Relay the server switch message
        relay.sendPlayerServerSwitch(player, fromServer, toServer);
    }
}
