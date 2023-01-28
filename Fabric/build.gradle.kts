plugins {
    id("fabric-loom") version "1.1-SNAPSHOT"
}

val minecraftVersion: String by project
val fabricLoaderVersion: String by project

val xplat = project(":Xplat")
val xplatMain = xplat.sourceSets.main.get()

loom {
    runs {
        named("client") {
            client()
            configName = "Fabric Client"
            ideConfigGenerated(true)
            runDir("run")
        }
        named("server") {
            server()
            configName = "Fabric Server"
            ideConfigGenerated(true)
            runDir("run")
        }
    }
}

dependencies {
    minecraft("com.mojang", "minecraft", minecraftVersion)
    mappings(loom.officialMojangMappings())

    modImplementation("net.fabricmc", "fabric-loader", fabricLoaderVersion)
    modImplementation("net.fabricmc.fabric-api", "fabric-api", "0.73.0+1.19.2")

    compileOnly(xplat)

    include(modImplementation("net.minecraftforge", "forgeconfigapiport-fabric", "4.2.10"))
    compileOnly("com.electronwill.night-config:core:3.6.5")
    compileOnly("com.electronwill.night-config:toml:3.6.5")
}

tasks {
    compileJava {
        source(xplatMain.allSource)
    }
    processResources {
        from(xplatMain.resources)

        val map =
            mapOf(
                "java" to java.targetCompatibility.majorVersion,
                "version" to project.version,
                "minecraft_required" to minecraftVersion
            )
        inputs.properties(map)

        filesMatching("fabric.mod.json") { expand(map) }
    }
}