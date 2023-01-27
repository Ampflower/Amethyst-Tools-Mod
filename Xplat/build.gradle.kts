plugins {
    java
    id("org.spongepowered.gradle.vanilla") version "0.2.1-SNAPSHOT"
}

val minecraftVersion: String by project

minecraft {
    version(minecraftVersion)
}

repositories {
    maven("https://api.modrinth.com/maven") {
        name = "Modrinth"
    }
}

dependencies {
    compileOnly("org.spongepowered", "mixin", "0.8.5")

    // Forge config API
    compileOnly("maven.modrinth", "forge-config-api-port", "4.2.10")
    compileOnly("com.electronwill.night-config:core:3.6.5")
    compileOnly("com.electronwill.night-config:toml:3.6.5")
}