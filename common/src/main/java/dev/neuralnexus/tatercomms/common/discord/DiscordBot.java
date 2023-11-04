package dev.neuralnexus.tatercomms.common.discord;

import dev.neuralnexus.tatercomms.common.TaterCommsConfig;
import dev.neuralnexus.tatercomms.common.discord.player.DiscordPlayer;
import dev.neuralnexus.tatercomms.common.TaterComms;
import dev.neuralnexus.tatercomms.common.relay.CommsMessage;
import dev.neuralnexus.tatercomms.common.relay.CommsRelay;
import dev.neuralnexus.taterlib.common.TaterLib;
import dev.neuralnexus.taterlib.common.placeholder.PlaceholderParser;
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

/**
 * The Discord bot.
 */
public class DiscordBot extends ListenerAdapter  {
    private final HashMap<String, String> serverChannels;
    private JDA api;

    /**
     * Constructor.
     * @param token The Discord bot token
     * @param serverChannels The server channels
     */
    public DiscordBot(String token, HashMap<String, String> serverChannels) {
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

    /**
     * Remove event listeners.
     */
    public void removeListeners() {
        api.removeEventListener(this);
    }

    /**
     * @inheritDoc
     */
    @Override
    public void onReady(ReadyEvent event) {
        TaterComms.useLogger("Discord bot is ready!");
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
        DiscordPlayer discordPlayer = new DiscordPlayer(author);

        // Send the message
        CommsRelay relay = (CommsRelay) TaterLib.getMessageRelay();
        HashMap<String, String> placeholders = new HashMap<>();
        placeholders.put("message", content);
        relay.relayMessage(new CommsMessage(discordPlayer,
                        CommsMessage.MessageType.PLAYER_MESSAGE,
                        content,
                        TaterCommsConfig.formattingChat().get("discord"),
                        placeholders
        ));
    }

    /**
     * Send system message to a Discord channel.
     * @param commsMessage The message to send
     */
    public void sendMessage(CommsMessage commsMessage) {
        String message = PlaceholderParser.stripSectionSign(commsMessage.applyPlaceHolders());
        String server = commsMessage.getSender().getServerName();

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

        // Send the message
        channel.sendMessage(message).queue();
    }
}
