package dev.neuralnexus.tatercomms.api;

import dev.neuralnexus.tatercomms.TaterComms;

/**
 * API Provider
 */
public class TaterCommsAPIProvider {
    private static TaterCommsAPI instance = null;

    /**
     * Get the instance of the API
     *
     * @return The instance of the API
     */
    public static TaterCommsAPI get() {
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
    public static void register(TaterCommsAPI instance) {
        if (TaterCommsAPIProvider.instance != null) {
            throw new IllegalStateException(
                    TaterComms.Constants.PROJECT_NAME + "API has already been registered!");
        }
        TaterCommsAPIProvider.instance = instance;
    }

    /**
     * DO NOT USE THIS METHOD, IT IS FOR INTERNAL USE ONLY
     */
    public static void unregister() {
        TaterCommsAPIProvider.instance = null;
    }

    /**
     * Throw this exception when the API hasn't loaded yet, or you don't have the plugin installed.
     */
    private static final class NotLoadedException extends IllegalStateException {
        private static final String MESSAGE =
                "The API hasn't loaded yet, or you don't have the "
                        + TaterComms.Constants.PROJECT_NAME
                        + " plugin installed.";

        NotLoadedException() {
            super(MESSAGE);
        }
    }
}
