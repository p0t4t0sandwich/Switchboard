/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.switchboard.api;

import dev.neuralnexus.switchboard.Switchboard;

/** API Provider */
public class SwitchboardAPIProvider {
    private static SwitchboardAPI instance = null;

    /**
     * Get the instance of the API
     *
     * @return The instance of the API
     */
    public static SwitchboardAPI get() {
        if (instance == null) {
            throw new NotLoadedException();
        }
        return instance;
    }

    /**
     * DO NOT USE THIS METHOD, IT IS FOR INTERNAL USE ONLY
     *
     * @param instance: The instance of the API
     */
    public static void register(SwitchboardAPI instance) {
        if (SwitchboardAPIProvider.instance != null) {
            throw new IllegalStateException(
                    Switchboard.Constants.PROJECT_NAME + "API has already been registered!");
        }
        SwitchboardAPIProvider.instance = instance;
    }

    /** DO NOT USE THIS METHOD, IT IS FOR INTERNAL USE ONLY */
    public static void unregister() {
        SwitchboardAPIProvider.instance = null;
    }

    /**
     * Throw this exception when the API hasn't loaded yet, or you don't have the plugin installed.
     */
    private static final class NotLoadedException extends IllegalStateException {
        private static final String MESSAGE =
                "The API hasn't loaded yet, or you don't have the "
                        + Switchboard.Constants.PROJECT_NAME
                        + " plugin installed.";

        NotLoadedException() {
            super(MESSAGE);
        }
    }
}
