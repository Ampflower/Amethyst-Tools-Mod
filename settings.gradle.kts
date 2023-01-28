rootProject.name = "Amethyst-Tools-Mod"

pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.minecraftforge.net/") {
            name = "Forge"
        }
        maven("https://maven.fabricmc.net/") {
            name = "Fabric"
        }
        maven("https://repo.spongepowered.org/repository/maven-public/") {
            name = "Sponge"
        }
    }
}

include("Xplat", "Forge", "Fabric")