/**
 * Copyright (c) 2025 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">MIT</a>
 */

package dev.neuralnexus.switchboard.modules.minecraft.command;

import static dev.neuralnexus.taterapi.placeholder.PlaceholderParser.substituteSectionSign;

import dev.neuralnexus.switchboard.SwitchboardPlugin;
import dev.neuralnexus.taterapi.TaterAPIProvider;
import dev.neuralnexus.taterapi.command.Command;
import dev.neuralnexus.taterapi.command.CommandSender;

/** Switchboard Command. */
public class SwitchboardCommand implements Command {
    private String name = "switchboard";

    @Override
    public String name() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String description() {
        return "Switchboard command";
    }

    @Override
    public String usage() {
        return "&a/switchboard <reload | version>";
    }

    @Override
    public String permission() {
        return "switchboard.admin";
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(substituteSectionSign(usage()));
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "reload":
                if (!TaterAPIProvider.hasPermission(sender, this.permission() + ".reload")) {
                    sender.sendMessage(
                            substituteSectionSign(
                                    "&cYou do not have permission to use this command."));
                    return true;
                }
                try {
                    SwitchboardPlugin.instance().reload();
                    sender.sendMessage(
                            substituteSectionSign("&aReloaded " + SwitchboardPlugin.PROJECT_NAME + "!"));
                } catch (Exception e) {
                    sender.sendMessage(
                            substituteSectionSign(
                                    "&cAn error occurred while reloading the plugin."));
                    e.printStackTrace();
                }
                break;
            case "version":
                if (!TaterAPIProvider.hasPermission(sender, this.permission() + ".version")) {
                    sender.sendMessage(
                            substituteSectionSign(
                                    "&cYou do not have permission to use this command."));
                    return true;
                }
                sender.sendMessage(
                        substituteSectionSign(
                                "&aSwitchboard version: " + SwitchboardPlugin.PROJECT_VERSION));
                break;
            default:
                sender.sendMessage(substituteSectionSign(this.usage()));
                break;
        }
        return true;
    }
}
