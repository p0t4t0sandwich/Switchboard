/**
 * Copyright (c) 2025 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">MIT</a>
 */

package dev.neuralnexus.switchboard.api.impl.discord;

import dev.neuralnexus.switchboard.api.Packet;
import dev.neuralnexus.switchboard.api.Sink;
import dev.neuralnexus.switchboard.api.SwitchboardAPI;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

public class DiscordSink implements Sink {
    @Override
    public String name() {
        return "discord";
    }

    @Override
    public void send(Packet packet) {
        String[] destIds = packet.sink().split("/");
        if (destIds.length != 2) {
            throw new IllegalArgumentException(
                    "Invalid sink, " + packet.sink() + ". expected format: <channelId>/<guildId>");
        }
        String guildId = destIds[0];
        String channelId = destIds[1];

        Guild guild = SwitchboardAPI.instance().discordAPI().api().getGuildById(guildId);
        if (guild == null) {
            throw new IllegalArgumentException("Invalid guild id: " + guildId);
        }
        TextChannel channel = guild.getTextChannelById(channelId);
        if (channel == null) {
            throw new IllegalArgumentException("Invalid channel id: " + channelId);
        }

        channel.sendMessage(packet.content()).queue();
    }
}
