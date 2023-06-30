# TaterComms

A simple, cross API plugin that bridges communication between servers, using built-in Proxy methods, Discord channels and websockets.

Link to our support: [Discord](https://discord.gg/NffvJd95tk)

## Download

- [GitHub](https://github.com/p0t4t0sandwich/TaterComms/releases)
- [Spigot](https://www.spigotmc.org/resources/tatercomms.110592/)
- [Hangar](https://hangar.papermc.io/p0t4t0sandwich/TaterComms)
- [Modrinth](https://modrinth.com/plugin/tatercomms)
- [CurseForge](https://www.curseforge.com/minecraft/mc-mods/tatercomms)

### Compatibility Cheatsheet

| Server type | Versions    | Jar Name                               |
|-------------|-------------|----------------------------------------|
| All 1.19    | 1.19-1.19.4 | `TaterComms-<version>-1.19.jar`        |
| All 1.20    | 1.20-1.20.1 | `TaterComms-<version>-1.20.jar`        |
| Bukkit      | 1.8-1.20.1  | `TaterComms-<version>-bukkit.jar`      |
| BungeeCord  | 1.20-1.20.1 | `TaterComms-<version>-bungee.jar`      |
| Velocity    | API v3      | `TaterComms-<version>-velocity.jar`    |
| Fabric 1.17 | 1.17-1.19.4 | `TaterComms-<version>-fabric-1.17.jar` |
| Fabric 1.20 | 1.20-1.20.1 | `TaterComms-<version>-fabric-1.20.jar` |
| Forge 1.19  | 1.19-1.19.4 | `TaterComms-<version>-forge-1.19.jar`  |
| Forge 1.20  | 1.20-1.20.1 | `TaterComms-<version>-forge-1.20.jar`  |

## Dependencies

This plugin has no strict dependencies, but it does have optional dependencies.

### Optional Dependencies

- [LuckPerms](https://luckperms.net/) - For user prefix/suffix support


## Commands and Permissions

| Command    | Permission           | Description                 |
|------------|----------------------|-----------------------------|
| `/discord` | `tatercomms.discord` | Get the Discord invite link |

## Configuration

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

## TODO

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

## Chat additions
- Remote chat websocket
- Complete chat management + passthrough configs
- Staff chat
- sync chats across servers/proxies in remote configuration
- RGB support for chat colors

# Release Notes
- Fixed an issue with the config parser
