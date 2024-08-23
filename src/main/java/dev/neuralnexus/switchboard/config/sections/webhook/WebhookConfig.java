package dev.neuralnexus.switchboard.config.sections.webhook;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

/**
 * A class for webhook configuration
 */
@ConfigSerializable
public class WebhookConfig {
    @Setting private String url;
    @Setting private String method;

    /**
     * Get the URL
     *
     * @return The URL
     */
    public String url() {
        return this.url;
    }

    /**
     * Get the HTTP method
     *
     * @return The HTTP method
     */
    public String method() {
        return this.method;
    }
}
