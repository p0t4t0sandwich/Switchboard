package dev.neuralnexus.tatercomms.modules.minecraft.command;

import dev.neuralnexus.tatercomms.TaterComms;
import dev.neuralnexus.taterlib.Utils;
import dev.neuralnexus.taterlib.command.Command;
import dev.neuralnexus.taterlib.command.CommandSender;

/** TaterComms Command. */
public class TaterCommsCommand implements Command {
    private String name = "tatercomms";

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
        return "TaterComms command";
    }

    @Override
    public String usage() {
        return "&a/tatercomms <reload | version>";
    }

    @Override
    public String permission() {
        return "tatercomms.admin";
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
                    TaterComms.reload();
                    sender.sendMessage(
                            Utils.substituteSectionSign(
                                    "&aReloaded " + TaterComms.Constants.PROJECT_NAME + "!"));
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
                                "&aTaterComms version: " + TaterComms.Constants.PROJECT_VERSION));
                break;
            default:
                sender.sendMessage(Utils.substituteSectionSign(usage()));
                break;
        }
        return true;
    }
}
