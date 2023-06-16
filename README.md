# TaterComms

A simple, cross API plugin that bridges communication between servers, using built-in Proxy methods, Discord channels and websockets.

Link to our support: [Discord](https://discord.gg/jec2jpdj7A)

## Download

- [GitHub](https://github.com/p0t4t0sandwich/TaterComms/releases)
- [Spigot](https://www.spigotmc.org/resources/template.xxxxxx/)
- [Hangar](https://hangar.papermc.io/p0t4t0sandwich/TaterComms)
- [Modrinth](https://modrinth.com/plugin/tatercomms)
- [CurseForge](https://www.curseforge.com/minecraft/mc-mods/tatercomms)

### Compatibility Cheatsheet

| Server type | Versions    | Jar Name                               |
|-------------|-------------|----------------------------------------|
| All 1.20    | 1.20-1.20.1 | `TaterComms-<version>-1.20.jar`        |
| Bukkit      | 1.8-1.20    | `TaterComms-<version>-bukkit.jar`      |
| BungeeCord  | 1.20-1.20   | `TaterComms-<version>-bungee.jar`      |
| Velocity    | API v3      | `TaterComms-<version>-velocity.jar`    |
| Fabric 1.20 | 1.20        | `TaterComms-<version>-fabric-1.20.jar` |
| Forge 1.20  | 1.20        | `TaterComms-<version>-forge-1.20.jar`  |

## Dependencies

This plugin requires [Dependency]() to function.

## Commands and Permissions

| Command                                    | Permission         | Description                  |
|--------------------------------------------|--------------------|------------------------------|
| `/command`                                 | `template.command` | Template command             |

## Configuration

```yaml
# Database configuration
# Supported storage types: mongodb, mysql
storage:
  type: mongodb
  config:
    host: localhost
    port: 27017
    database: template
    username: root
    password: password
```

## TODO

- global chat options
- chat formatting options (placeholders in the config)
- grab the prefix + suffix from LuckPerms
- Staff chat
- custom group chats
- account linking system
- discord channels accessible from in game, chatroom style (for linked users)
- sync chats across servers/proxies in remote configuration
- mail system
- Advancement listener
  - Filter specific advancement roots?
  - Proxies would need a plugin channel to pass the event up
- Server starting/stopping listeners
- placeholders
  - serverName
  - playerName
  - playerDisplayName
  - playerPrefix
  - playerSuffix
  - message
  - advancementName
