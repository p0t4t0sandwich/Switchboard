import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

import java.time.Instant

plugins {
    id("java")
    id("com.diffplug.spotless") version("7.0.1")
    id("com.gradleup.shadow") version("9.0.0-beta4")
}

java {
    withSourcesJar()
    withJavadocJar()
    toolchain.languageVersion = JavaLanguageVersion.of(21)
    sourceCompatibility = JavaVersion.toVersion(21)
    targetCompatibility = JavaVersion.toVersion(21)
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://maven.neuralnexus.dev/releases")
    maven("https://maven.neuralnexus.dev/snapshots")
    maven("https://maven.neuralnexus.dev/mirror")
}

dependencies {
    // Discord
    implementation("net.dv8tion:JDA:5.0.0-beta.19") {
        exclude(module = "opus-java")
    }
    implementation("org.slf4j:slf4j-api:1.7.32")
    implementation("org.slf4j:slf4j-simple:1.7.32")

    // Telegram
    implementation("com.github.pengrad:java-telegram-bot-api:7.1.0")

    // API/WebSocket server
    implementation("io.javalin:javalin:6.4.0")
    implementation("com.neovisionaries:nv-websocket-client:2.14")

    compileOnly("dev.neuralnexus:taterapi:2.0.0-SNAPSHOT")
    compileOnly("dev.neuralnexus:entrypoint-spoof:0.1.5")

    compileOnly("org.spongepowered:configurate-hocon:4.1.2")
    compileOnly("com.google.code.gson:gson:2.10.1")
    compileOnly("com.google.guava:guava:33.0.0-jre")
}

tasks.withType<ProcessResources> {
    filesMatching(
        listOf(
            "plugin.yml",
            "bungee.yml",
            "fabric.mod.json",
            "META-INF/mods.toml",
            "META-INF/neoforge.mods.toml",
            "mcmod.info",
            "META-INF/sponge_plugins.json",
            "velocity-plugin.json"
        )
    ) {
        expand(project.properties)
    }
}

spotless {
    format("misc", {
        target("*.gradle", ".gitattributes", ".gitignore")

        trimTrailingWhitespace()
        leadingTabsToSpaces()
        endWithNewline()
    })
    java {
        importOrder()
        removeUnusedImports()
        cleanthat()
        googleJavaFormat("1.17.0").aosp().formatJavadoc(true).reorderImports(true)
        formatAnnotations()
        licenseHeader("""/**
 * Copyright (c) 2025 Dylan Sperrer - dylan@sperrer.ca
 * The project is Licensed under <a href="https://github.com/p0t4t0sandwich/Switchboard/blob/dev/LICENSE">MIT</a>
 */

""")
    }
}

tasks.named<ShadowJar>("shadowJar") {
    dependencies {
        exclude("META-INF/maven/**")
        exclude("META-INF/proguard/**")
        exclude("META-INF/services/**")
        exclude("META-INF/versions/**")
        exclude("META-INF/*LICENSE*")
        exclude("META-INF/*NOTICE*")
        exclude("META-INF/*kotlin_module")

        exclude(dependency("com.google.code.gson:gson:2.10.1"))
    }
    relocate("org.intellij", "${group}.${name}.lib.intellij")
    relocate("org.jetbrains", "${group}.${name}.lib.jetbrains")
    relocate("net.dv8tion", "${group}.${name}.lib.dv8tion")
    relocate("org.apache.commons", "${group}.${name}.lib.commons")
    relocate("okhttp3", "${group}.${name}.lib.okhttp3")
    relocate("okio", "${group}.${name}.lib.okio")
    relocate("kotlin", "${group}.${name}.lib.kotlin")
    relocate("com.neovisionaries", "${group}.${name}.lib.neovisionaries")
    relocate("gnu.trove", "${group}.${name}.lib.trove")
    relocate("com.iwebpp", "${group}.${name}.lib.iwebpp")
    relocate("com.fasterxml.jackson", "${group}.${name}.lib.jackson")
    relocate("org.slf4j", "${group}.${name}.lib.slf4j") {
        exclude("org.slf4j.Logger")
    }

    relocate("com.pengrad.telegrambot", "${group}.${name}.lib.telegrambot")

    relocate("io.javalin", "${group}.${name}.lib.javalin")

    // TaterLib bundled dependencies
    relocate("org.spongepowered.configurate", "dev.neuralnexus.taterlib.lib.configurate")
    relocate("com.typesafe.config", "dev.neuralnexus.taterlib.lib.typesafe.config")
    relocate("io.leangen.geantyref", "dev.neuralnexus.taterlib.lib.geantyref")
    relocate("com.google.gson", "dev.neuralnexus.taterlib.lib.gson")
    relocate("com.google.common", "dev.neuralnexus.taterlib.lib.guava")
    relocate("com.google.thirdparty", "dev.neuralnexus.taterlib.lib.google.thirdparty")

    minimize() {
        exclude(dependency("${group}:${name}:${version}"))
    }

    archiveFileName = "${name}-${version}.jar"

    manifest {
        attributes(
            mapOf(
                "Specification-Title" to name,
                "Specification-Version" to version,
                "Specification-Vendor" to "NeualNexus",
                "Implementation-Version" to version,
                "Implementation-Vendor" to "NeualNexus",
                "Implementation-Timestamp" to Instant.now().toString()
            )
        )
    }

    from(listOf("README.md", "LICENSE")) {
        into("META-INF")
    }
}

tasks.build.get().dependsOn(tasks.spotlessApply)

artifacts {
    archives(tasks.shadowJar)
}
