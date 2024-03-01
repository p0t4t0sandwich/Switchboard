/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.switchboard.config.sections.telegram;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

/** Telegram chat channel. */
@ConfigSerializable
public class ChatChannel {
    @Setting("chatTitle")
    private String chatTitle;

    @Setting("chatId")
    private long chatId;

    /**
     * Get the chat id.
     *
     * @return The chat id
     */
    public long chatId() {
        return chatId;
    }

    /**
     * Set the chat id.
     *
     * @param chatId The chat id
     */
    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    /**
     * Get the chat title.
     *
     * @return The chat title
     */
    public String chatTitle() {
        return chatTitle;
    }
}
