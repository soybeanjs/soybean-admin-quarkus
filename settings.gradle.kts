pluginManagement {
    val quarkusPluginVersion: String by settings
    val quarkusPluginId: String by settings
    val kotlinVersion: String by settings
    val kspVersion: String by settings
    repositories {
        mavenCentral()
        gradlePluginPortal()
        mavenLocal()
    }
    plugins {
        id(quarkusPluginId) version quarkusPluginVersion
    }
    resolutionStrategy {
        eachPlugin {
            when (requested.id.id) {
                "org.jetbrains.kotlin.jvm" -> useVersion(kotlinVersion)
                "org.jetbrains.kotlin.plugin.allopen" -> useVersion(kotlinVersion)
                "com.google.devtools.ksp" -> useVersion(kspVersion)
                "org.jetbrains.kotlin.kapt" -> useVersion(kotlinVersion)
            }
        }
    }
}
rootProject.name = "soybean-admin-backend"