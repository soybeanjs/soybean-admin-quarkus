plugins {
    alias(libs.plugins.kotlin)
}

repositories {
    mavenCentral()
    mavenLocal()
}

val quarkusPluginId: String by project
val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project
val projectGroup: String by project
val projectVersion: String by project

group = projectGroup
version = projectVersion

subprojects {
    repositories {
        mavenCentral()
        mavenLocal()
    }

    apply(plugin = "org.jetbrains.kotlin.jvm")

    dependencies {
        implementation(platform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
        implementation(platform("${quarkusPlatformGroupId}:quarkus-camel-bom:${quarkusPlatformVersion}"))
    }

    group = project.group
    version = projectVersion

    plugins.withId(quarkusPluginId) {
        val kubernetesDir = layout.buildDirectory.dir("kubernetes")
        kubernetesDir.get().asFile.let { dir ->
            if (dir.exists()) {
                val targetDir = project.projectDir.resolve("kubernetes")

                val copyKubernetesDir by tasks.registering(Copy::class) {
                    from(dir)
                    into(targetDir)

                    doFirst {
                        if (!targetDir.exists()) {
                            targetDir.mkdirs()
                        }
                    }

                    doLast {
                        println("Kubernetes directory has been copied to: $targetDir")
                    }
                }

                tasks.named("quarkusBuild") {
                    finalizedBy(copyKubernetesDir)
                }
            }
        }
    }
}
