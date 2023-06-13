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

public class DiscordBot extends ListenerAdapter  {
    private final HashMap<String, String> serverChannels;
    private final JDA api;
    private final String guildId;
    private Guild guild;

    public DiscordBot(String token, String guildId, HashMap<String, String> serverChannels) {
        this.serverChannels = serverChannels;
        this.guildId = guildId;

        // Check for nulls
        if (token == null || token.equals("")) {
            throw new RuntimeException("Discord token not found, please check the config!");
        } else if (serverChannels == null || serverChannels.isEmpty()) {
            throw new RuntimeException("Server channels not found, please check the config!");
        }

        // Create the JDA instance
        api = JDABuilder.createDefault(token).enableIntents(GatewayIntent.MESSAGE_CONTENT).build();

        // Add the listener
        api.addEventListener(this);
    }

    @Override
    public void onReady(ReadyEvent event) {
        System.out.println("Discord bot is ready!");
        // Get the guild
        guild = api.getGuildById(guildId);
        if (guild == null) {
            System.err.println("Guild not found, please check that the bot is in the guild, and that the guild ID is correct in the config!");
        }
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

        // Get the channel
        String channelID = message.getChannel().getId();
        String server = null;
        for (String key : serverChannels.keySet()) {
            if (serverChannels.get(key).equals(channelID)) {
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
        String channelID = serverChannels.get(server);
        if (channelID == null) {
            return;
        }

        // Get the channel
        TextChannel channel = guild.getTextChannelById(channelID);
        if (channel == null) {
            throw new RuntimeException("Channel not found for server " + server + ", please check the config!");
        }

        // Format the message
        String msg = "**" + player.getDisplayName() + "**: " + message;

        // Send the message
        channel.sendMessage(msg).queue();
    }
}
