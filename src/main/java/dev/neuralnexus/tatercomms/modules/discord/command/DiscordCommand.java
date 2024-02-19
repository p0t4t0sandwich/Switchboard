package dev.neuralnexus.tatercomms.modules.discord.command;

import dev.neuralnexus.tatercomms.TaterCommsConfig;
import dev.neuralnexus.taterlib.Utils;
import dev.neuralnexus.taterlib.command.Command;
import dev.neuralnexus.taterlib.command.CommandSender;

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
        return "tatercomms.command.discord";
    }

    @Override
    public boolean execute(CommandSender sender, String label, String[] args) {
        if (!sender.hasPermission(permission())) {
            sender.sendMessage(
                    Utils.substituteSectionSign(
                            "&cYou do not have permission to use this command."));
        } else {
            sender.sendMessage(
                    Utils.substituteSectionSign(TaterCommsConfig.DiscordConfig.inviteUrl()));
        }
        return true;
    }
}
