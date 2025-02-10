/**
 * Copyright (c) 2025 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">MIT</a>
 */

package dev.neuralnexus.switchboard.api.impl.discord;

import dev.neuralnexus.switchboard.Switchboard;
import dev.neuralnexus.switchboard.config.SwitchboardConfigLoader;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.session.ReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

import org.jetbrains.annotations.NotNull;

/** API for the Discord module. */
public class DiscordAPI {
    private Bot bot = null;

    public JDA api() {
        return this.bot.api();
    }

    /** Start the bot. */
    public void startBot() {
        if (bot == null) {
            this.bot = new Bot();
        }
    }

    /** Remove the bot. */
    public void removeBot() {
        if (this.bot != null) {
            this.bot.removeListeners();
        }
        this.bot = null;
    }

    static class Bot extends ListenerAdapter {
        private JDA api;

        public JDA api() {
            return this.api;
        }

        Bot() {
            try {
                // Create the JDA instance
                api =
                        JDABuilder.createDefault(SwitchboardConfigLoader.config().discord().token())
                                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                                .build();

                // Add the listener
                api.addEventListener(this);
                api.addEventListener(new DiscordSource());
            } catch (Exception e) {
                Switchboard.logger().error("Failed to start Discord Bot!", e);
            }
        }

        public void removeListeners() {
            api.removeEventListener(this);
        }

        @Override
        public void onReady(@NotNull ReadyEvent event) {
            Switchboard.logger().info("Discord bot is ready!");
        }
    }
}
