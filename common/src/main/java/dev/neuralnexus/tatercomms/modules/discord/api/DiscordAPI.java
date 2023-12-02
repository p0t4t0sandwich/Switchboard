package dev.neuralnexus.tatercomms.modules.discord.api;

import dev.neuralnexus.tatercomms.TaterComms;
import dev.neuralnexus.tatercomms.TaterCommsConfig;
import dev.neuralnexus.tatercomms.api.TaterCommsAPIProvider;
import dev.neuralnexus.tatercomms.api.message.Message;
import dev.neuralnexus.tatercomms.event.ReceiveMessageEvent;
import dev.neuralnexus.tatercomms.event.api.TaterCommsEvents;
import dev.neuralnexus.taterlib.entity.Entity;
import dev.neuralnexus.taterlib.inventory.PlayerInventory;
import dev.neuralnexus.taterlib.placeholder.PlaceholderParser;
import dev.neuralnexus.taterlib.player.Player;
import dev.neuralnexus.taterlib.utils.Location;

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
                TaterComms.getLogger().info("Failed to start Discord Bot!");
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
            TaterComms.getLogger().info("Discord bot is ready!");
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

            // Get the author
            User author = message.getAuthor();
            DiscordPlayer discordPlayer = new DiscordPlayer(author);

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
            String server = message.getSender().getServerName();

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
    public static class DiscordPlayer implements Player {
        private final User user;
        private final String name;
        private final String displayName;
        private final String serverName;

        /**
         * Constructor.
         *
         * @param user The Discord user.
         */
        public DiscordPlayer(User user) {
            this.user = user;
            this.name = user.getName();
            this.displayName = user.getEffectiveName();
            this.serverName = "discord";
        }

        /**
         * @inheritDoc
         */
        @Override
        public String getName() {
            return this.name;
        }

        /**
         * @inheritDoc
         */
        @Override
        public String getDisplayName() {
            return this.displayName;
        }

        /**
         * @inheritDoc
         */
        @Override
        public UUID getUniqueId() {
            return UUID.fromString("00000000-0000-0000-0000-000000000000");
        }

        /**
         * @inheritDoc
         */
        @Override
        public String getServerName() {
            return this.serverName;
        }

        /**
         * @inheritDoc
         */
        @Override
        public void setServerName(String serverName) {}

        /**
         * @inheritDoc
         */
        @Override
        public void sendMessage(String message) {
            user.openPrivateChannel().queue(channel -> channel.sendMessage(message).queue());
        }

        @Override
        public int getEntityId() {
            return 0;
        }

        @Override
        public void remove() {}

        @Override
        public String getType() {
            return null;
        }

        @Override
        public String getCustomName() {
            return null;
        }

        @Override
        public void setCustomName(String s) {}

        @Override
        public Location getLocation() {
            return null;
        }

        @Override
        public double getX() {
            return 0;
        }

        @Override
        public double getY() {
            return 0;
        }

        @Override
        public double getZ() {
            return 0;
        }

        @Override
        public float getYaw() {
            return 0;
        }

        @Override
        public float getPitch() {
            return 0;
        }

        @Override
        public String getDimension() {
            return null;
        }

        @Override
        public String getBiome() {
            return null;
        }

        @Override
        public void teleport(Location location) {}

        @Override
        public void teleport(Entity entity) {}

        @Override
        public boolean hasPermission(int i) {
            return false;
        }

        /**
         * @inheritDoc
         */
        @Override
        public void sendPluginMessage(String channel, byte[] message) {}

        /**
         * @inheritDoc
         */
        @Override
        public PlayerInventory getInventory() {
            return null;
        }

        /**
         * @inheritDoc
         */
        @Override
        public void kickPlayer(String message) {}

        @Override
        public void setSpawn(Location location, boolean b) {}

        @Override
        public void setSpawn(Location location) {}
    }
}
