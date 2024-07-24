/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.switchboard.modules.discord.command;

import static dev.neuralnexus.taterapi.util.TextUtil.substituteSectionSign;

import dev.neuralnexus.switchboard.config.SwitchboardConfigLoader;
import dev.neuralnexus.taterapi.command.Command;
import dev.neuralnexus.taterapi.command.CommandSender;

/** Discord Command. */
public class DiscordCommand implements Command {
    private String name = "discord";

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
        return "Get the discord invite link";
    }

    @Override
    public String usage() {
        return "/discord";
    }

    @Override
    public String permission() {
        return "switchboard.command.discord";
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!sender.hasPermission(permission())) {
            sender.sendMessage(
                    substituteSectionSign("&cYou do not have permission to use this command."));
        } else {
            sender.sendMessage(
                    substituteSectionSign(SwitchboardConfigLoader.config().discord().inviteUrl()));
        }
        return true;
    }
}
