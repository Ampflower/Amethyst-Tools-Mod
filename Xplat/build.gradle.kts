plugins {
    java
    id("org.spongepowered.gradle.vanilla") version "0.2.1-SNAPSHOT"
}

val minecraftVersion: String by project

minecraft {
    version(minecraftVersion)
}

dependencies {
    compileOnly("org.spongepowered", "mixin", "0.8.5")

    // Forge config API
    compileOnly("net.minecraftforge", "forgeconfigapiport-fabric", "4.2.10")
    compileOnly("com.electronwill.night-config:core:3.6.5")
    compileOnly("com.electronwill.night-config:toml:3.6.5")
}