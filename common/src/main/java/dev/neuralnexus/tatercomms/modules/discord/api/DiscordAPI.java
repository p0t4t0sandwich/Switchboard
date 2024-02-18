package dev.neuralnexus.tatercomms.modules.discord.api;

import dev.neuralnexus.tatercomms.TaterComms;
import dev.neuralnexus.tatercomms.TaterCommsConfig;
import dev.neuralnexus.tatercomms.api.TaterCommsAPIProvider;
import dev.neuralnexus.tatercomms.api.message.Message;
import dev.neuralnexus.tatercomms.event.ReceiveMessageEvent;
import dev.neuralnexus.tatercomms.event.api.TaterCommsEvents;
import dev.neuralnexus.taterlib.placeholder.PlaceholderParser;
import dev.neuralnexus.taterlib.player.Player;
import dev.neuralnexus.taterlib.player.SimplePlayer;
import dev.neuralnexus.taterlib.server.SimpleServer;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

/** API for the Discord module. */
public class DiscordAPI {
    private static Bot bot = null;

    /** Start the bot. */
    public void startBot() {
        if (bot == null) {
            bot = new Bot();
        }
    }

    /** Remove the bot. */
    public void removeBot() {
        if (bot != null) {
            bot.removeListeners();
        }
        bot = null;
    }

    /**
     * Send a message to a Discord channel
     *
     * @param message The message
     */
    public void sendMessage(Message message) {
        if (bot != null) {
            bot.sendMessage(message);
        }
    }

    static class Bot extends ListenerAdapter {
        private JDA api;

        Bot() {
            try {
                // Create the JDA instance
                api =
                        JDABuilder.createDefault(TaterCommsConfig.DiscordConfig.token())
                                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                                .build();

                // Add the listener
                api.addEventListener(this);
            } catch (Exception e) {
                TaterComms.logger().info("Failed to start Discord Bot!");
                e.printStackTrace();
            }
        }

        /** Remove event listeners. */
        public void removeListeners() {
            api.removeEventListener(this);
        }

        /**
         * @inheritDoc
         */
        @Override
        public void onReady(ReadyEvent event) {
            TaterComms.logger().info("Discord bot is ready!");
        }

        /**
         * Converts the Discord message to a Message object, then invokes the ReceiveMessageEvent.
         *
         * @param event The event
         */
        @Override
        public void onMessageReceived(MessageReceivedEvent event) {
            if (event.getAuthor().isBot()) return;

            // Get the message
            net.dv8tion.jda.api.entities.Message message = event.getMessage();
            String content = message.getContentRaw();

            // Get the guild and channel
            String guildID = message.getGuild().getId();
            String channelID = message.getChannel().getId();

            String server = null;
            for (String key : TaterCommsConfig.DiscordConfig.channels().keySet()) {
                if (TaterCommsConfig.DiscordConfig.channels()
                        .get(key)
                        .equals(guildID + "/" + channelID)) {
                    server = key;
                    break;
                }
            }

            // Check if the channel is a server channel
            if (server == null) {
                return;
            }

            // Create player abstraction
            DiscordPlayer discordPlayer = new DiscordPlayer(message);

            // Send the message
            HashMap<String, String> placeholders = new HashMap<>();
            placeholders.put("message", content);
            TaterCommsEvents.RECEIVE_MESSAGE.invoke(
                    new ReceiveMessageEvent(
                            new Message(
                                    discordPlayer,
                                    Message.MessageType.PLAYER_MESSAGE,
                                    content,
                                    TaterCommsAPIProvider.get().getFormatting("discord"),
                                    placeholders)));
        }

        /**
         * Send a message to a Discord channel
         *
         * @param message The message
         */
        public void sendMessage(Message message) {
            String messageContent = PlaceholderParser.stripSectionSign(message.applyPlaceHolders());
            String server = message.getSender().server().name();

            // Get the channel
            String channelGuildId = TaterCommsConfig.DiscordConfig.channels().get(server);
            if (channelGuildId == null) {
                return;
            }

            // Get the guild and channel
            Guild guild = api.getGuildById(channelGuildId.split("/")[0]);
            if (guild == null) {
                System.err.println(
                        "Guild not found for server " + server + ", please check the config!");
                return;
            }
            TextChannel channel = guild.getTextChannelById(channelGuildId.split("/")[1]);
            if (channel == null) {
                System.err.println(
                        "Channel not found for server " + server + ", please check the config!");
                return;
            }

            // Send the message
            channel.sendMessage(messageContent).queue();
        }
    }

    /** Discord implementation of {@link Player}. */
    public static class DiscordPlayer implements SimplePlayer {
        private final User user;
        private final String name;
        private final String displayName;
        private final DiscordServer server;

        /**
         * Constructor.
         *
         * @param message The message
         */
        public DiscordPlayer(net.dv8tion.jda.api.entities.Message message) {
            this.user = message.getAuthor();
            this.name = this.user.getName();
            this.displayName = this.user.getEffectiveName();
            this.server = new DiscordServer(message);
        }

        /**
         * @inheritDoc
         */
        @Override
        public String name() {
            return this.name;
        }

        /**
         * @inheritDoc
         */
        @Override
        public String displayName() {
            return this.displayName;
        }

        @Override
        public String ipAddress() {
            return "";
        }

        @Override
        public int ping() {
            return 0;
        }

        @Override
        public void kick(String s) {}

        /**
         * @inheritDoc
         */
        @Override
        public UUID uuid() {
            return UUID.fromString("00000000-0000-0000-0000-000000000000");
        }

        @Override
        public SimpleServer server() {
            return this.server;
        }

        /**
         * @inheritDoc
         */
        @Override
        public void sendMessage(String message) {
            user.openPrivateChannel().queue(channel -> channel.sendMessage(message).queue());
        }

        @Override
        public boolean hasPermission(int i) {
            return false;
        }

        @Override
        public void sendPluginMessage(String channel, byte[] message) {}
    }

    public static class DiscordServer implements SimpleServer {
        private final String guildId;
        private final String channelId;
        private final String name;

        public DiscordServer(net.dv8tion.jda.api.entities.Message message) {
            this.guildId = message.getGuild().getId();
            this.channelId = message.getChannel().getId();
            this.name = message.getGuild().getName();
        }

        @Override
        public String name() {
            return this.name;
        }

        @Override
        public String brand() {
            return "Discord";
        }

        @Override
        public Set<SimplePlayer> onlinePlayers() {
            return null;
        }
    }
}
