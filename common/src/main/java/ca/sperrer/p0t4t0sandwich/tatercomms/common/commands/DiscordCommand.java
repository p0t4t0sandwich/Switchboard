package ca.sperrer.p0t4t0sandwich.tatercomms.common.commands;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.TaterComms;

public interface DiscordCommand {
    static String getCommandName() {
        return "discord";
    }

    static String getCommandDescription() {
        return "Get the link to the Discord server.";
    }

    static String getCommandUsage() {
        return "Usage: /discord";
    }

    static String getCommandPermission() {
        return "tatercomms.discord";
    }

    static String executeCommand(String[] args) {
        if (args.length != 0) {
            return getCommandUsage();
        } else {
            return TaterComms.getDiscordInviteLink();
        }
    }
}
