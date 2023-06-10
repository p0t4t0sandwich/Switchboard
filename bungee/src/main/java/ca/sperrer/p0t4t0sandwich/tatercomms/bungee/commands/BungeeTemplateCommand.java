package ca.sperrer.p0t4t0sandwich.tatercomms.bungee.commands;

import ca.sperrer.p0t4t0sandwich.tatercomms.bungee.BungeeMain;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

import static ca.sperrer.p0t4t0sandwich.tatercomms.common.Utils.runTaskAsync;

public class BungeeTemplateCommand extends Command {
    private final BungeeMain plugin = BungeeMain.getInstance();

    public BungeeTemplateCommand() {
        super("template");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        runTaskAsync(() -> {
            try {
                // Check if sender is a player
                if ((sender instanceof ProxiedPlayer)) {
                    ProxiedPlayer player = (ProxiedPlayer) sender;

                    // Permission check
                    if (!player.hasPermission("template.command")) {
                        player.sendMessage(new ComponentBuilder("§cYou do not have permission to use this command.").create());
                        return;
                    }

                    String text = "";

                    player.sendMessage(new ComponentBuilder(text).create());
                } else {
                    sender.sendMessage(new ComponentBuilder("§cYou must be a player to use this command.").create());
                }
            } catch (Exception e) {
                System.err.println(e);
                e.printStackTrace();
            }
        });
    }
}
