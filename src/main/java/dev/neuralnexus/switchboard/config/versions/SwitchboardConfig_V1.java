/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.switchboard.config.versions;

import dev.neuralnexus.switchboard.config.SwitchboardConfig;
import dev.neuralnexus.switchboard.config.sections.discord.DiscordConfig;
import dev.neuralnexus.switchboard.config.sections.formatting.FormattingConfig;
import dev.neuralnexus.switchboard.config.sections.telegram.TelegramConfig;
import dev.neuralnexus.switchboard.config.sections.webhook.WebhookConfig;
import dev.neuralnexus.switchboard.config.sections.websocket.WebSocketConfig;

import org.spongepowered.configurate.objectmapping.meta.Comment;
import org.spongepowered.configurate.objectmapping.meta.Required;

import java.util.HashMap;
import java.util.Map;

/** A class for Switchboard configuration. */
public class SwitchboardConfig_V1 implements SwitchboardConfig {
    @Comment("Config version, DO NOT CHANGE THIS")
    @Required
    private int version = 1;

    @Comment("""
            Enable or disable Switchboard's modules
            discord: Enable if you want the server to relay messages to/from Discord
            proxy: Whether the plugin is running in a proxy network
                Set this to true on both ends to enable plugin messaging for some events (player advancements, death messages, and other server-side-only events.)
            telegram: Enable if you want the server to relay messages to/from Telegram
            webhook: Send messages as webhook/POST/GET request to some configured endpoint
            websocket: Remote WebSocket configuration (for servers that you can't run behind a proxy)
                Fun fact: if you're having issues running Forge 1.13+ behind a proxy, check out Ambassador: https://github.com/adde0109/Ambassador
                Short explanation: The 1.13 update changed the way that Forge initializes and syncs modded data with the server, this causes issues with the way that the proxy works
            """)
    private Map<String, Boolean> modules = new HashMap<>();
    {
        modules.put("discord", false);
        modules.put("proxy", false);
        modules.put("telegram", false);
        modules.put("webhook", false);
        modules.put("websocket", false);
    }

    private DiscordConfig discord;
    private TelegramConfig telegram;
    private WebhookConfig webhook;
    private WebSocketConfig webSocket;
    private FormattingConfig formatting;

    @Override
    public int version() {
        return this.version;
    }

    @Override
    public Map<String, Boolean> modules() {
        return this.modules;
    }

    @Override
    public DiscordConfig discord() {
        return this.discord;
    }

    @Override
    public TelegramConfig telegram() {
        return this.telegram;
    }

    @Override
    public WebhookConfig webhook() {
        return this.webhook;
    }

    @Override
    public WebSocketConfig webSocket() {
        return this.webSocket;
    }

    @Override
    public FormattingConfig formatting() {
        return this.formatting;
    }
}
