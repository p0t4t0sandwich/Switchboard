package dev.neuralnexus.tatercomms.config.sections.formatting;

import org.spongepowered.configurate.objectmapping.ConfigSerializable;
import org.spongepowered.configurate.objectmapping.meta.Setting;

/** A class for handling message formatting configuration. */
@ConfigSerializable
public class FormattingConfig {
    @Setting private String advancement;
    @Setting private String death;
    @Setting private String login;
    @Setting private String logout;

    @Setting("serverStarted")
    private String serverStarted;

    @Setting("serverStopped")
    private String serverStopped;

    @Setting private String discord;
    @Setting private String global;
    @Setting private String local;
    @Setting private String remote;

    public String advancement() {
        return advancement;
    }

    public String death() {
        return death;
    }

    public String login() {
        return login;
    }

    public String logout() {
        return logout;
    }

    public String serverStarted() {
        return serverStarted;
    }

    public String serverStopped() {
        return serverStopped;
    }

    public String discord() {
        return discord;
    }

    public String global() {
        return global;
    }

    public String local() {
        return local;
    }

    public String remote() {
        return remote;
    }
}
