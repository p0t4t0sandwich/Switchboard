package dev.neuralnexus.tatercomms.common;

import dev.neuralnexus.taterlib.common.TemplatePlugin;

/**
 * The TaterComms plugin interface.
 */
public interface TaterCommsPlugin extends TemplatePlugin {
    /**
     * Starts the TaterComms plugin.
     */
    default void pluginStart() {
        try {
            useLogger("TaterComms is running on " + getServerType() + " " + getServerVersion() + "!");

            // Start
            TaterComms.start(pluginConfigPath(), pluginLogger());

            // Register hooks
            registerHooks();

            // Register event listeners
            registerEventListeners();

            // Register commands
            registerCommands();

        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }

    /**
     * Stops the plugin.
     */
    default void pluginStop() {
        try {
            TaterComms.stop();
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
    }
}
