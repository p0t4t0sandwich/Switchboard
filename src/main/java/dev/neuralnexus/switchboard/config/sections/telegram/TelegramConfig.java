/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.switchboard.config.sections.telegram;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

import java.util.Optional;
import java.util.Set;

/** Telegram configuration. */
@ConfigSerializable
public class TelegramConfig {
    @Setting private String token;
    @Setting private Set<ChatMapping> mappings;

    /**
     * Get the token.
     *
     * @return The token
     */
    public String token() {
        return token;
    }

    /**
     * Get the mappings.
     *
     * @return The mappings
     */
    public Set<ChatMapping> mappings() {
        return mappings;
    }

    /**
     * Get the chat mappings for a server.
     *
     * @param server The server name
     * @return The chat mappings
     */
    public Optional<ChatMapping> getMappings(String server) {
        for (ChatMapping mapping : mappings) {
            if (mapping.server().equals(server)) {
                return Optional.of(mapping);
            }
        }
        return Optional.empty();
    }

    /**
     * Find a server name by chatId.
     *
     * @param chatId The chat id
     * @param chatTitle The chat id
     * @return The server name
     */
    public Optional<String> getServerName(long chatId, String chatTitle) {
        for (ChatMapping mapping : mappings) {
            for (ChatChannel channel : mapping.channels()) {
                if (channel.chatTitle().equals(chatTitle)) {
                    channel.setChatId(chatId);
                    return Optional.of(mapping.server());
                }
            }
        }
        return Optional.empty();
    }
}
