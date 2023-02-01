import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import java.io.FilterReader
import java.io.Reader
import java.io.StringReader

plugins {
    id("fabric-loom") version "1.1-SNAPSHOT"
}

val minecraftVersion: String by project
val fabricLoaderVersion: String by project

val xplat = project(":Xplat")
val xplatMain = xplat.sourceSets.main.get()


loom {
    mixin.defaultRefmapName.set("amethysttoolsmod.refmap.json")

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

        filesMatching("data/amethysttoolsmod/recipes/*.json") {
            filter(Carrier.JsonTransformer::class)
        }
    }
}

object Carrier {
    val remapper = mapOf(
        "forge:not" to "fabric:not",
        "forge:and" to "fabric:and",
        "forge:or" to "fabric:or"
    )

    fun remap(condition: JsonObject) {
        condition.remove("type").asString.let { type ->
            condition.addProperty("condition", remapper.getOrDefault(type, type))

            if (remapper.containsKey(type)) {
                (condition["value"] as? JsonObject)?.let(::remap)
                (condition["values"] as? JsonArray)?.forEach { if (it is JsonObject) remap(it) }
            }
        }
    }

    class JsonTransformer(it: Reader) : FilterReader(run {
        val element = JsonParser.parseReader(it)
        if (element !is JsonObject || element["type"].asString != "forge:conditional") {
            return@run StringReader(element.toString())
        }

        val recipeArray = element.getAsJsonArray("recipes")
        if (recipeArray.size() != 1) {
            throw IllegalStateException("Invalid recipe array; $recipeArray")
        }

        val recipeWrapper = recipeArray[0].asJsonObject
        val recipeRoot = recipeWrapper.getAsJsonObject("recipe")

        val loadConditions = recipeWrapper.getAsJsonArray("conditions")
        for (condition in loadConditions) {
            remap(condition as JsonObject)
        }

        recipeRoot.add("fabric:load_conditions", loadConditions)

        StringReader(recipeRoot.toString())
    })
}