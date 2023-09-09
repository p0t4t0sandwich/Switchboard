# TaterComms

[![License](https://img.shields.io/github/license/p0t4t0sandwich/TaterComms?color=blue)](https://img.shields.io/github/downloads/p0t4t0sandwich/TaterComms/LICENSE)
[![Github](https://img.shields.io/github/stars/p0t4t0sandwich/TaterComms)](https://github.com/p0t4t0sandwich/TaterComms)
[![Github Issues](https://img.shields.io/github/issues/p0t4t0sandwich/TaterComms?label=Issues)](https://github.com/p0t4t0sandwich/TaterComms/issues)
[![Discord](https://img.shields.io/discord/1067482396246683708?color=7289da&logo=discord&logoColor=white)](https://discord.neuralnexus.dev)
[![wakatime](https://wakatime.com/badge/user/fc67ce74-ca69-40a4-912f-61b26dbe3068/project/c722f2dd-f37e-4e20-9b32-e00d4d8ec34b.svg)](https://wakatime.com/badge/user/fc67ce74-ca69-40a4-912f-61b26dbe3068/project/c722f2dd-f37e-4e20-9b32-e00d4d8ec34b)

A simple, cross API plugin that bridges communication between servers, using built-in Proxy methods, Discord channels and websockets.

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

### Compatibility Cheatsheet

TaterComms supports: Bukkit, Fabric, Forge, and Sponge (some versions)

| Server type        | Versions    | Jar Name                        |
|--------------------|-------------|---------------------------------|
| All 1.20           | 1.20-1.20.1 | `TaterComms-1.20-<version>.jar` |
| All 1.19           | 1.19-1.19.4 | `TaterComms-1.19-<version>.jar` |
| All 1.18           | 1.18-1.18.2 | `TaterComms-1.18-<version>.jar` |
| All 1.17           | 1.17-1.17.1 | `TaterComms-1.17-<version>.jar` |
| All 1.16 (Sponge8) | 1.16-1.16.5 | `TaterComms-1.16-<version>.jar` |
| All 1.15           | 1.15-1.15.2 | `TaterComms-1.15-<version>.jar` |

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
version: 1

# Discord bot configuration
# You only need one of these per server network, assuming you're running a primary proxy/websocket to handle chats
# If you're running a standalone server, or this is the main server/proxy in your network,
# set primary to true and set the channel mappings accordingly.
discord:
  enabled: true
  token: ""
  inviteUrl: "&6Join our Discord: &ahttps://discord.gg/yourInvite"

  # Server to Discord Channel mapping
  channels:
    # in the format of: serverName: guildID/channelId
    example: "123456789012345678/123456789012345678"

# Allow specific servers to bypass the chat formatting.
# The backend server will handle all the chat messages, but the players will still see the messages from other servers.
passthrough:
  servers:
    - example1
    - example2

# ServerName for standalone servers (Bukkit, Fabric, or Forge)
# Proxies will use the server names defined in the config
server:
  name: "example"

formatting:
  # %player% - Player name
  # %message% - Message
  # %server% - Server name
  # %prefix% - Player prefix
  # %suffix% - Player suffix
  # %displayname% - Player display name

  # Global chat formatting
  global: "&a[G]&r %displayname% >> %message%"
  # Local chat formatting
  local: "&a[L]&r %displayname% >> %message%"
  # Staff chat formatting
  staff: "&1[S]&r %displayname% >> %message%"
  # Discord chat formatting
  discord: "&9[D]&r %displayname% >> %message%"
```

# TODO
- api to add messages/events to the relay

## Listeners
- Events that need to be sent up to the proxy
  - Player Advancement
  - Player Death

## Discord additions
- account linking system
  - translate discord names to minecraft names and vice versa
- configurable embeds
  - use player head as icon
    - cache the head
- option to use webhooks for discord
- add option for webhook-only, and just webhook options in general

## Chat additions
- Remote chat websocket
- Complete chat management + passthrough configs
- Staff chat
- sync chats across servers/proxies in remote configuration
- RGB support for chat colors
- bell noise “ping”

# Release Notes
- Recoded to use TaterLib for easier cross-API support
- Once again depends on the fabric API
- Back ported to 1.14 and added Sponge8 support
