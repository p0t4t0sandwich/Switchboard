# TaterComms

[![License](https://img.shields.io/github/license/p0t4t0sandwich/TaterComms?color=blue)](https://img.shields.io/github/downloads/p0t4t0sandwich/TaterComms/LICENSE)
[![Github](https://img.shields.io/github/stars/p0t4t0sandwich/TaterComms)](https://github.com/p0t4t0sandwich/TaterComms)
[![Github Issues](https://img.shields.io/github/issues/p0t4t0sandwich/TaterComms?label=Issues)](https://github.com/p0t4t0sandwich/TaterComms/issues)
[![Discord](https://img.shields.io/discord/1067482396246683708?color=7289da&logo=discord&logoColor=white)](https://discord.neuralnexus.dev)
[![wakatime](https://wakatime.com/badge/user/fc67ce74-ca69-40a4-912f-61b26dbe3068/project/c722f2dd-f37e-4e20-9b32-e00d4d8ec34b.svg)](https://wakatime.com/badge/user/fc67ce74-ca69-40a4-912f-61b26dbe3068/project/c722f2dd-f37e-4e20-9b32-e00d4d8ec34b)

A simple, cross API plugin that bridges communication between servers, using built-in Proxy methods, Discord channels and TCP sockets.

Link to our support: [Discord](https://discord.neuralnexus.dev)

## Download

[![Github Releases](https://img.shields.io/github/downloads/p0t4t0sandwich/TaterComms/total?label=Github&logo=github&color=181717)](https://github.com/p0t4t0sandwich/TaterComms/releases)
[![Maven Repo](https://img.shields.io/maven-metadata/v?label=Release&metadataUrl=https%3A%2F%2Fmaven.neuralnexus.dev%2Freleases%2Fdev%2Fneuralnexus%2FTaterComms%2Fmaven-metadata.xml)](https://maven.neuralnexus.dev/#/releases/dev/neuralnexus/TaterComms)
[![Maven Repo](https://img.shields.io/maven-metadata/v?label=Snapshot&metadataUrl=https%3A%2F%2Fmaven.neuralnexus.dev%2Fsnapshots%2Fdev%2Fneuralnexus%2FTaterComms%2Fmaven-metadata.xml)](https://maven.neuralnexus.dev/#/snapshots/dev/neuralnexus/TaterComms)

[![Spigot](https://img.shields.io/spiget/downloads/110592?label=Spigot&logo=spigotmc&color=ED8106)](https://www.spigotmc.org/resources/tatercomms.110592/)
[![Hangar](https://img.shields.io/badge/Hangar-download-blue)](https://hangar.papermc.io/p0t4t0sandwich/TaterComms)
[![Modrinth](https://img.shields.io/modrinth/dt/tatercomms?label=Modrinth&logo=modrinth&color=00AF5C)](https://modrinth.com/mod/tatercomms)
[![CurseForge](https://img.shields.io/curseforge/dt/877133?label=CurseForge&logo=curseforge&color=F16436)](https://www.curseforge.com/minecraft/mc-mods/tatercomms)
[![Sponge](https://img.shields.io/ore/dt/tatercomms?label=Sponge&logo=https%3A%2F%2Fspongepowered.org%2Ffavicon.ico&color=F7CF0D)](https://ore.spongepowered.org/p0t4t0sandwich/TaterComms)

### Known Issues

- Global chat still needs some tweaking and a proper, per-user toggle
- Sponge death/advancement messages don't translate the component properly
- Still need to tweak the pass-through system and get that working properly
- Bukkit b1.7.3 doesn't have proper death/advancement events, so those won't work

### Compatibility Cheatsheet

TaterComms supports: Bukkit, Fabric, Forge, and Sponge (some versions)

| Server type         | Versions    | Jar Name                        |
|---------------------|-------------|---------------------------------|
| All 1.20 (Sponge11) | 1.20-1.20.1 | `TaterComms-1.20-<version>.jar` |
| All 1.19 (Sponge10) | 1.19-1.19.4 | `TaterComms-1.19-<version>.jar` |
| All 1.18 (Sponge9)  | 1.18-1.18.2 | `TaterComms-1.18-<version>.jar` |
| All 1.17 (Sponge9)  | 1.17-1.17.1 | `TaterComms-1.17-<version>.jar` |
| All 1.16 (Sponge8)  | 1.16-1.16.5 | `TaterComms-1.16-<version>.jar` |
| All 1.15 (Sponge8)  | 1.15-1.15.2 | `TaterComms-1.15-<version>.jar` |
| All 1.14            | 1.14-1.14.3 | `TaterComms-1.14-<version>.jar` |

## Dependencies

- [TaterLib](https://github.com/p0t4t0sandwich/TaterLib) - Required on all platforms
- [FabricAPI](https://modrinth.com/mod/fabric-api) - Required on Fabric

### Optional Dependencies

- [LuckPerms](https://luckperms.net/) - For permissions/prefix/suffix support

## Usage

- [Create and add a Discord bot to your server](https://discordpy.readthedocs.io/en/stable/discord.html)

## Commands and Permissions

| Command    | Permission                   | Description                 |
|------------|------------------------------|-----------------------------|
| `/discord` | `tatercomms.command.discord` | Get the Discord invite link |

## Configuration

<plugins/config>/TaterComms/tatercomms.config.yml

```yaml
---
version: 2

# ServerName for standalone servers (Bukkit, Fabric, or Forge)
# Proxies will use the server names defined in the config
server:
  name: "serverName"
  # Weather the plugin is running in a proxy network
  # Set this to true on both ends to enable plugin messaging for some events (player advancements, death messages, etc.)
  usingProxy: false
  # Weather global chat should be the default chat channel for players
  globalChatEnabledByDefault: false

# Discord bot configuration
# You only need one of these per server network, assuming you're running a primary proxy/socket to handle chats
# If you're running a standalone server, or this is the main server/proxy in your network, set primary to true and set the channel mappings accordingly
discord:
  enabled: true
  token: ""
  inviteUrl: "&6Join our Discord: &ahttps://discord.gg/yourInvite"

  # Server to Discord Channel mapping
  channels:
    # in the format of serverName: guildID/channelId
    global: "123456789012345678/123456789012345678"
    serverName: "123456789012345678/123456789012345678"

# Formatting for server messages
formatting:
  # Whether TaterComms should handle chat formatting
  enabled: false
  # Allow specific servers to bypass chat formatting
  # The backend server will handle all the chat formatting
  passthrough:
    - example1
    - example2
  chat:
    # Common placeholders for player events/messages:
    #   %player% - Player name
    #   %displayname% - Player display name
    #   %message% - Message
    #   %server% - Server name
    #   %prefix% - Player prefix
    #   %suffix% - Player suffix

    # Player advancement messages
    #   %advancement% - Advancement name
    advancement: "%displayname% has made the advancement [%advancement%]!"
    # Player death messages
    #   %deathmessage% - Death message
    death: "%deathmessage%"
    # Player login messages
    login: "%displayname% joined the game"
    # Player logout messages
    logout: "%displayname% left the game"

    # Server start message
    serverStarted: "**Server Started**"
    # Server stop message
    serverStopped: "**Server Stopped**"

    # Formatting for messages coming from Discord
    discord: "&9[D]&r %displayname% >> %message%"

    # Message formatting for each type of chat
    #   %message% - Message
    # Global chat formatting
    global: "&a[G]&r %displayname% >> %message%"
    # Local chat formatting
    local: "&e[L]&r %displayname% >> %message%"
    # Remote chat formatting
    remote: "&4[%server%]&r %displayname% >> %message%"

# Remote TCP socket configuration (for servers that you can't run behind a proxy)
# Fun fact: if you're having issues running Forge 1.13+ behind a proxy, check out Ambassador: https://github.com/adde0109/Ambassador
# Short explanation: The 1.13 update changed the way that Forge initializes and syncs mod/datapack data with the server, this causes issues with the way that the proxy works
remote:
  enabled: false
  primary: false
  host: 127.0.0.1
  port: 5483
  secret: ""
```

# Release Notes v1.0.3

- Recoded to use TaterLib for easier cross-API support
- Once again depends on the fabric API
- Back ported to 1.14 and added support for Sponge 8-11
- Added plugin message support for server-side only events
- Implemented plugin channel support for all platforms
- Overhauled the message relay system
  - Added Remote TCP socket support
