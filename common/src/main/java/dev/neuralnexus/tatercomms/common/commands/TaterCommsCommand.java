package dev.neuralnexus.tatercomms.common.commands;

import dev.neuralnexus.tatercomms.common.TaterComms;
import dev.neuralnexus.taterlib.common.abstractions.player.AbstractPlayer;
import dev.neuralnexus.taterlib.common.placeholder.PlaceholderParser;

import static dev.neuralnexus.taterlib.common.Utils.ansiiParser;


public interface TaterCommsCommand {
    static String getCommandName() {
        return "tatercomms";
    }

    static String getCommandDescription() {
        return "TaterComms management command.";
    }

    static String getCommandUsage() {
        return "&cUsage: /tatercomms reload";
    }

    static String permissionBuilder(String[] args) {
        if (args.length == 0) {
            return "tatercomms.admin";
        } else if (args.length == 1) {
            return "tatercomms.admin." + args[0].toLowerCase();
        } else if (args.length == 2) {
            return "tatercomms.admin." + args[0].toLowerCase() + "." + args[1].toLowerCase();
        } else {
            return "tatercomms.admin." + args[0].toLowerCase() + "." + args[1].toLowerCase() + "." + args[2].toLowerCase();
        }
    }

    static String executeCommand(String[] args) {
        String text;
        if (args.length == 0) {
            return getCommandUsage();
        }
        switch (args[0].toLowerCase()) {
            case "reload":
                try {
                    // Try to reload the plugin
                    TaterComms.reload();
                    text = "&aReloaded TaterComms.";
                } catch (Exception e) {
                    text = "&cAn error occurred while reloading the plugin.";
                    e.printStackTrace();
                }
                break;
            default:
                text = getCommandUsage();
                break;
        }
        return PlaceholderParser.substituteSectionSign(text);
    }

    static void executeCommand(AbstractPlayer player, boolean isPlayer, String[] args) {
        if (isPlayer) {
            if (!player.hasPermission(permissionBuilder(args))) {
                player.sendMessage("§cYou do not have permission to use this command.");
            } else {
                player.sendMessage(executeCommand(args));
            }
        } else {
            TaterComms.useLogger(ansiiParser(executeCommand(args)));
        }
    }
}
