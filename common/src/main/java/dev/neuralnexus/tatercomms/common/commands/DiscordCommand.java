package dev.neuralnexus.tatercomms.common.commands;

import dev.neuralnexus.tatercomms.common.TaterComms;
import dev.neuralnexus.tatercomms.common.TaterCommsConfig;
import dev.neuralnexus.taterlib.common.abstractions.player.AbstractPlayer;
import dev.neuralnexus.taterlib.common.placeholder.PlaceholderParser;

import static dev.neuralnexus.taterlib.common.Utils.ansiiParser;

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
        return "tatercomms.command.discord";
    }

    static String executeCommand(String[] args) {
        if (args.length != 0) {
            return getCommandUsage();
        } else {
            return new PlaceholderParser(TaterCommsConfig.discordInviteUrl()).parseSectionSign().getResult();
        }
    }

    static void executeCommand(AbstractPlayer player, boolean isPlayer, String[] args) {
        if (isPlayer) {
            if (!player.hasPermission(getCommandPermission())) {
                player.sendMessage("§cYou do not have permission to use this command.");
            } else {
                player.sendMessage(executeCommand(args));
            }
        } else {
            TaterComms.useLogger(ansiiParser(executeCommand(args)));
        }
    }
}
