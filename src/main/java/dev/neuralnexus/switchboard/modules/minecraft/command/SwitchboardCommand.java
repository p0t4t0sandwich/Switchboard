/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.switchboard.modules.minecraft.command;

import dev.neuralnexus.switchboard.Switchboard;
import dev.neuralnexus.taterlib.Utils;
import dev.neuralnexus.taterlib.command.Command;
import dev.neuralnexus.taterlib.command.CommandSender;

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
            sender.sendMessage(Utils.substituteSectionSign(usage()));
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "reload":
                if (!sender.hasPermission(permission() + ".reload")) {
                    sender.sendMessage(
                            Utils.substituteSectionSign(
                                    "&cYou do not have permission to use this command."));
                    return true;
                }
                try {
                    Switchboard.reload();
                    sender.sendMessage(
                            Utils.substituteSectionSign(
                                    "&aReloaded " + Switchboard.Constants.PROJECT_NAME + "!"));
                } catch (Exception e) {
                    sender.sendMessage(
                            Utils.substituteSectionSign(
                                    "&cAn error occurred while reloading the plugin."));
                    e.printStackTrace();
                }
                break;
            case "version":
                if (!sender.hasPermission(permission() + ".version")) {
                    sender.sendMessage(
                            Utils.substituteSectionSign(
                                    "&cYou do not have permission to use this command."));
                    return true;
                }
                sender.sendMessage(
                        Utils.substituteSectionSign(
                                "&aSwitchboard version: " + Switchboard.Constants.PROJECT_VERSION));
                break;
            default:
                sender.sendMessage(Utils.substituteSectionSign(usage()));
                break;
        }
        return true;
    }
}
