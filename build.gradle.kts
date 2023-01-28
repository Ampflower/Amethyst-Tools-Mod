import java.text.SimpleDateFormat
import java.util.*

plugins {
    java
}

val minecraftVersion: String by project
val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
val buildTimestamp = dateFormat.format(Date())!!
val modId = "amethysttoolsmod"

subprojects {
    // This helps with ordering. Removal may cause compilation issues.
    apply(plugin = "java")

    java {
        toolchain.languageVersion.set(JavaLanguageVersion.of(17))
    }

    repositories {
        maven("https://raw.githubusercontent.com/Fuzss/modresources/main/maven/") { name = "Fuzs Mod Resources" }
        maven("https://api.modrinth.com/maven") { name = "Modrinth" }
    }

    tasks {
        jar {
            manifest {
                attributes(
                    mapOf(
                        "Specification-Title" to modId,
                        "Specification-Vendor" to "macaronsteam",
                        "Specification-Version" to "$minecraftVersion-$archiveVersion",
                        "Implementation-Title" to modId,
                        "Implementation-Vendor" to "macaronsteam",
                        "Implementation-Version" to "$minecraftVersion-$archiveVersion",
                        "Implementation-Timestamp" to buildTimestamp,
                    )
                )
            }
        }
    }
}