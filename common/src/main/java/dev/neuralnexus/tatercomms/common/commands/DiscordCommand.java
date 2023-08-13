package dev.neuralnexus.tatercomms.common.commands;

import dev.neuralnexus.tatercomms.common.TaterComms;
import dev.neuralnexus.taterlib.common.placeholder.PlaceholderParser;

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
            return new PlaceholderParser(TaterComms.getDiscordInviteLink()).parseSectionSign().getResult();
        }
    }
}
