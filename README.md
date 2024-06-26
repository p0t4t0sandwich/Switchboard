# Switchboard

[![License](https://img.shields.io/github/license/p0t4t0sandwich/Switchboard?color=blue)](https://img.shields.io/github/downloads/p0t4t0sandwich/Switchboard/LICENSE)
[![Github](https://img.shields.io/github/stars/p0t4t0sandwich/Switchboard)](https://github.com/p0t4t0sandwich/Switchboard)
[![Github Issues](https://img.shields.io/github/issues/p0t4t0sandwich/Switchboard?label=Issues)](https://github.com/p0t4t0sandwich/Switchboard/issues)
[![Discord](https://img.shields.io/discord/1067482396246683708?color=7289da&logo=discord&logoColor=white)](https://discord.neuralnexus.dev)
[![wakatime](https://wakatime.com/badge/user/fc67ce74-ca69-40a4-912f-61b26dbe3068/project/c722f2dd-f37e-4e20-9b32-e00d4d8ec34b.svg)](https://wakatime.com/badge/user/fc67ce74-ca69-40a4-912f-61b26dbe3068/project/c722f2dd-f37e-4e20-9b32-e00d4d8ec34b)

A simple, cross API plugin that bridges communication between servers, using built-in Proxy methods, Discord channels
and TCP sockets.

Link to our support: [Discord](https://discord.neuralnexus.dev)

## Download

[![Github Releases](https://img.shields.io/github/downloads/p0t4t0sandwich/Switchboard/total?label=Github&logo=github&color=181717)](https://github.com/p0t4t0sandwich/Switchboard/releases)
[![Maven Repo](https://img.shields.io/maven-metadata/v?label=Release&metadataUrl=https%3A%2F%2Fmaven.neuralnexus.dev%2Freleases%2Fdev%2Fneuralnexus%2FSwitchboard%2Fmaven-metadata.xml)](https://maven.neuralnexus.dev/#/releases/dev/neuralnexus/Switchboard)
[![Maven Repo](https://img.shields.io/maven-metadata/v?label=Snapshot&metadataUrl=https%3A%2F%2Fmaven.neuralnexus.dev%2Fsnapshots%2Fdev%2Fneuralnexus%2FSwitchboard%2Fmaven-metadata.xml)](https://maven.neuralnexus.dev/#/snapshots/dev/neuralnexus/Switchboard)

[![Spigot](https://img.shields.io/spiget/downloads/110592?label=Spigot&logo=spigotmc&color=ED8106)](https://www.spigotmc.org/resources/switchboard.110592/)
[![Hangar](https://img.shields.io/badge/Hangar-download-blue)](https://hangar.papermc.io/p0t4t0sandwich/Switchboard)
[![Modrinth](https://img.shields.io/modrinth/dt/switchboard?label=Modrinth&logo=modrinth&color=00AF5C)](https://modrinth.com/plugin/switchboard)
[![CurseForge](https://img.shields.io/curseforge/dt/877133?label=CurseForge&logo=curseforge&color=F16436)](https://www.curseforge.com/minecraft/mc-mods/switchboard)
[![Sponge](https://img.shields.io/ore/dt/switchboard?label=Sponge&logo=https%3A%2F%2Fspongepowered.org%2Ffavicon.ico&color=F7CF0D)](https://ore.spongepowered.org/p0t4t0sandwich/Switchboard)

## Usage

- [Configuration guide wiki entry](https://github.com/p0t4t0sandwich/Switchboard/wiki/Configuration-Guide)
- [Create and add a Discord bot to your server](https://discordpy.readthedocs.io/en/stable/discord.html)
- The bot will need permissions to read and send messages with whatever permission scopes you set up, and in the
  channels you want to use

## Known Issues

- Under certain circumstances with a proxy, prefix/suffix information is not read correctly, still narrowing down the
  cause
- Global chat still needs some tweaking and a proper, per-user toggle
- Sponge death messages are a tad off with how the component serializes (eg: `playerName Skeleton was shot by` instead
  of `playerName was shot by Skeleton`)
- Still need to tweak the pass-through system and get that working properly

## Dependencies

- [TaterLib](https://github.com/p0t4t0sandwich/TaterLib)

### Compatibility

- Platforms: Bukkit, BungeeCord, Fabric, Forge, Sponge, and Velocity
- Versions: Any version that TaterLib supports

## Commands and Permissions

| Command              | Permission                   | Description                 |
|----------------------|------------------------------|-----------------------------|
| `/switchboard reload` | `switchboard.admin.reload`    | Reload the plugin           |
| `/discord`           | `switchboard.command.discord` | Get the Discord invite link |

## Metrics

### Bukkit

![image](https://bstats.org/signatures/bukkit/Switchboard.svg)

### BungeeCord

![image](https://bstats.org/signatures/bungeecord/Switchboard.svg)

### Sponge

![image](https://bstats.org/signatures/sponge/Switchboard.svg)

### Velocity

![image](https://bstats.org/signatures/velocity/Switchboard.svg)

## Release Notes

### v1.0.4-R0.2-SNAPSHOT

- Fixed bug where the plugin didn't unregister the Discord bot events when reloading the plugin
- Complete rewrite using nifty additions to TaterLib
