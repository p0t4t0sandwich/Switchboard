/**
 * Copyright (c) 2025 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">MIT</a>
 */

package dev.neuralnexus.switchboard.api.impl.discord;

import dev.neuralnexus.switchboard.Switchboard;
import dev.neuralnexus.switchboard.api.Message;
import dev.neuralnexus.switchboard.api.MessageTypes;
import dev.neuralnexus.switchboard.api.Source;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DiscordSource extends ListenerAdapter implements Source {
    @Override
    public String name() {
        return "discord";
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;

        // Get the message
        net.dv8tion.jda.api.entities.Message message = event.getMessage();
        String content = message.getContentRaw();

        // Get the guild and channel
        String guildID = message.getGuild().getId();
        String channelID = message.getChannel().getId();

        // Publish the message
        Switchboard.bus()
                .publish(
                        new Message(
                                guildID + "/" + channelID,
                                message.getAuthor().getName(),
                                MessageTypes.MESSAGE,
                                content));
    }
}
