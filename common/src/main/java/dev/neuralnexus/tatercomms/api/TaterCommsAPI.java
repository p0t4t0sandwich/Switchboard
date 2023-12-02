package dev.neuralnexus.tatercomms.api;

import dev.neuralnexus.tatercomms.TaterCommsConfig;
import dev.neuralnexus.tatercomms.modules.discord.api.DiscordAPI;
import dev.neuralnexus.tatercomms.modules.socket.api.SocketAPI;

import java.util.HashMap;

/** API wrapper class */
public class TaterCommsAPI {
    private final Data data;
    private DiscordAPI discordAPI = null;
    private SocketAPI socketAPI = null;

    public TaterCommsAPI() {
        this.data = new Data();
        if (TaterCommsConfig.isModuleEnabled("discord")) {
            this.discordAPI = new DiscordAPI();
        }
        if (TaterCommsConfig.isModuleEnabled("socket")) {
            this.socketAPI = new SocketAPI();
        }
    }

    /**
     * Get the Discord API.
     *
     * @return The Discord API.
     */
    public DiscordAPI discordAPI() {
        return discordAPI;
    }

    /**
     * Get the Socket API.
     *
     * @return The Socket API.
     */
    public SocketAPI socketAPI() {
        return socketAPI;
    }

    /**
     * Get the server name.
     *
     * @return The server name.
     */
    public String getServerName() {
        return data.getServerName();
    }

    /**
     * Set the server name.
     *
     * @param serverName The server name.
     */
    public void setServerName(String serverName) {
        data.setServerName(serverName);
    }

    /**
     * Get the formatting.
     *
     * @param key The key.
     * @return The formatting.
     */
    public String getFormatting(String key) {
        String formatting = data.getFormatting().get(key);
        return formatting == null ? "%message%" : formatting;
    }

    /**
     * Set the formatting.
     *
     * @param formatting The formatting.
     */
    public void setFormatting(HashMap<String, String> formatting) {
        data.setFormatting(formatting);
    }

    /**
     * Get whether the server is using a proxy.
     *
     * @return Whether the server is using a proxy.
     */
    public boolean isUsingProxy() {
        return data.isUsingProxy();
    }

    /**
     * Set whether the server is using a proxy.
     *
     * @param usingProxy Whether the server is using a proxy.
     */
    public void setUsingProxy(boolean usingProxy) {
        data.setUsingProxy(usingProxy);
    }

    /** The data for the API. */
    static class Data {
        private String serverName = "";
        private HashMap<String, String> formatting = new HashMap<>();
        private boolean usingProxy = false;

        /**
         * Get the server name.
         *
         * @return The server name.
         */
        public String getServerName() {
            return serverName;
        }

        /**
         * Set the server name.
         *
         * @param serverName The server name.
         */
        public void setServerName(String serverName) {
            this.serverName = serverName;
        }

        /**
         * Get the formatting.
         *
         * @return The formatting.
         */
        public HashMap<String, String> getFormatting() {
            return formatting;
        }

        /**
         * Set the formatting.
         *
         * @param formatting The formatting.
         */
        public void setFormatting(HashMap<String, String> formatting) {
            this.formatting = formatting;
        }

        /**
         * Get whether the server is using a proxy.
         *
         * @return Whether the server is using a proxy.
         */
        public boolean isUsingProxy() {
            return usingProxy;
        }

        /**
         * Set whether the server is using a proxy.
         *
         * @param usingProxy Whether the server is using a proxy.
         */
        public void setUsingProxy(boolean usingProxy) {
            this.usingProxy = usingProxy;
        }
    }
}
