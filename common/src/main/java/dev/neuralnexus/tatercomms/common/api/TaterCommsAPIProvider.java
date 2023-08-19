package dev.neuralnexus.tatercomms.common.api;

import dev.neuralnexus.tatercomms.common.TaterComms;

/**
 * TaterComms API Provider
 */
public class TaterCommsAPIProvider {
    private static TaterComms instance = null;

    /**
     * Get the instance of BeeNameGenerator
     * @return The instance of BeeNameGenerator
     */
    public static TaterComms get() {
        if (instance == null) {
            throw new NotLoadedException();
        }
        return instance;
    }

    /**
     * DO NOT USE THIS METHOD, IT IS FOR INTERNAL USE ONLY
     * @param instance: The instance of TaterComms
     */
    public static void register(TaterComms instance) {
        TaterCommsAPIProvider.instance = instance;
    }

    /**
     * DO NOT USE THIS METHOD, IT IS FOR INTERNAL USE ONLY
     */
    public static void unregister() {
        TaterCommsAPIProvider.instance = null;
    }

    /**
     * Throw this exception when the API hasn't loaded yet, or you don't have the BeeNameGenerator plugin installed.
     */
    private static final class NotLoadedException extends IllegalStateException {
        private static final String MESSAGE = "The API hasn't loaded yet, or you don't have the TaterComms plugin installed.";

        NotLoadedException() {
            super(MESSAGE);
        }
    }
}
