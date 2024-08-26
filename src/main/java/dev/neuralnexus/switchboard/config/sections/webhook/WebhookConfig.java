/**
 * Copyright (c) 2024 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">GPL-3</a>
 * The API is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE-API">MIT</a>
 */

package dev.neuralnexus.switchboard.config.sections.webhook;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

/** A class for webhook configuration */
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
