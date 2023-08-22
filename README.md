# TaterComms

A simple, cross API plugin that bridges communication between servers, using built-in Proxy methods, Discord channels and websockets.

Link to our support: [Discord](https://discord.neuralnexus.dev)

## Download

- [GitHub](https://github.com/p0t4t0sandwich/TaterComms/releases)
- [Spigot](https://www.spigotmc.org/resources/tatercomms.110592/)
- [Hangar](https://hangar.papermc.io/p0t4t0sandwich/TaterComms)
- [Modrinth](https://modrinth.com/plugin/tatercomms)
- [CurseForge](https://www.curseforge.com/minecraft/mc-mods/tatercomms)
- [Sponge](https://ore.spongepowered.org/p0t4t0sandwich/TaterComms)

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
