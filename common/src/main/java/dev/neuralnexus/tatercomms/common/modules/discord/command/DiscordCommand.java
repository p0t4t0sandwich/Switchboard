package dev.neuralnexus.tatercomms.common.modules.discord.command;

import dev.neuralnexus.tatercomms.common.TaterCommsConfig;
import dev.neuralnexus.taterlib.common.Utils;
import dev.neuralnexus.taterlib.common.command.Command;
import dev.neuralnexus.taterlib.common.command.Sender;

/** Discord Command. */
public class DiscordCommand implements Command {
    private String name = "discord";

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getDescription() {
        return "Get the discord invite link";
    }

    @Override
    public String getUsage() {
        return "/discord";
    }

    @Override
    public String getPermission() {
        return "tatercomms.command.discord";
    }

    @Override
    public boolean execute(Sender sender, String label, String[] args) {
        if (!sender.hasPermission(getPermission())) {
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
