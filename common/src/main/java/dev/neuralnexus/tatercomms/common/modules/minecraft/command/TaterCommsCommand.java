package dev.neuralnexus.tatercomms.common.modules.minecraft.command;

import dev.neuralnexus.tatercomms.common.TaterComms;
import dev.neuralnexus.tatercomms.common.TaterCommsConfig;
import dev.neuralnexus.taterlib.common.Utils;
import dev.neuralnexus.taterlib.common.command.Command;
import dev.neuralnexus.taterlib.common.command.Sender;

/**
 * TaterComms Command.
 */
public class TaterCommsCommand implements Command {
    private String name = "tatercomms";

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return "TaterComms command";
    }

    @Override
    public String getUsage() {
        return "&a/tatercomms <reload | version>";
    }

    @Override
    public String getPermission() {
        return "tatercomms.admin";
    }

    @Override
    public String execute(String[] args) {
        return null;
    }

    @Override
    public boolean execute(Sender sender, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(Utils.substituteSectionSign(getUsage()));
            return true;
        }
        switch (args[0].toLowerCase()) {
            case "reload":
                if (!sender.hasPermission(getPermission() + ".reload")) {
                    sender.sendMessage(Utils.substituteSectionSign("&cYou do not have permission to use this command."));
                    return true;
                }
                try {
                    TaterComms.reload();
                    sender.sendMessage(Utils.substituteSectionSign("&aReloaded " + TaterComms.Constants.PROJECT_NAME + "!"));
                } catch (Exception e) {
                    sender.sendMessage(Utils.substituteSectionSign("&cAn error occurred while reloading the plugin."));
                    e.printStackTrace();
                }
                break;
            case "version":
                if (!sender.hasPermission(getPermission() + ".version")) {
                    sender.sendMessage(Utils.substituteSectionSign("&cYou do not have permission to use this command."));
                    return true;
                }
                sender.sendMessage(Utils.substituteSectionSign("&aTaterComms version: " + TaterComms.Constants.PROJECT_VERSION));
                break;
            default:
                sender.sendMessage(Utils.substituteSectionSign(getUsage()));
                break;
        }
        return true;
    }
}
