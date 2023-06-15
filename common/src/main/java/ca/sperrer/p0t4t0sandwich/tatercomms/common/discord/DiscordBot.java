package ca.sperrer.p0t4t0sandwich.tatercomms.common.discord;

import ca.sperrer.p0t4t0sandwich.tatercomms.common.discord.player.DiscordTaterPlayer;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.player.TaterPlayer;
import ca.sperrer.p0t4t0sandwich.tatercomms.common.relay.MessageRelay;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.HashMap;

import static ca.sperrer.p0t4t0sandwich.tatercomms.common.Utils.runTaskAsync;

public class DiscordBot extends ListenerAdapter  {
    private final HashMap<String, String> serverChannels;
    private JDA api;

    public DiscordBot(String token, String guildId, HashMap<String, String> serverChannels) {
        this.serverChannels = serverChannels;

        try {
            // Create the JDA instance
            api = JDABuilder.createDefault(token).enableIntents(GatewayIntent.MESSAGE_CONTENT).build();

            // Add the listener
            api.addEventListener(this);
        } catch (Exception e) {
            System.err.println("Failed to start TaterComms Discord Bot!");
            System.err.println(e);
            e.printStackTrace();
        }
    }

    @Override
    public void onReady(ReadyEvent event) {
        System.out.println("Discord bot is ready!");
    }

    /**
     * Sends a Discord message over to the Minecraft server.
     * @param event The event
     */
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        // Get the message
        Message message = event.getMessage();
        String content = message.getContentRaw();

        // Get the guild and channel
        String guildID = message.getGuild().getId();
        String channelID = message.getChannel().getId();

        String server = null;
        for (String key : serverChannels.keySet()) {
            if (serverChannels.get(key).equals(guildID + "/" + channelID)) {
                server = key;
                break;
            }
        }

        // Check if the channel is a server channel
        if (server == null) {
            return;
        }

        // Get the author
        User author = message.getAuthor();
        DiscordTaterPlayer taterPlayer = new DiscordTaterPlayer(author);

        // Send the message
        MessageRelay.getInstance().receiveMessage(taterPlayer, server, content);
    }

    /**
     * Sends a message to a Discord channel.
     * @param server The server to send the message to
     * @param message The message to send
     */
    public void sendMessage(TaterPlayer player, String server, String message) {
        // Get the channel
        String channelGuildId = serverChannels.get(server);
        if (channelGuildId == null) {
            return;
        }

        // Get the guild and channel
        Guild guild = api.getGuildById(channelGuildId.split("/")[0]);
        if (guild == null) {
            System.err.println("Guild not found for server " + server + ", please check the config!");
            return;
        }
        TextChannel channel = guild.getTextChannelById(channelGuildId.split("/")[1]);
        if (channel == null) {
            System.err.println("Channel not found for server " + server + ", please check the config!");
            return;
        }

        // Format the message
        String msg = "**" + player.getDisplayName() + "**: " + message;

        // Send the message
        channel.sendMessage(msg).queue();
    }
}
