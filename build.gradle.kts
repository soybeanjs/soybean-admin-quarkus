plugins {
    alias(libs.plugins.kotlin)
    id("com.diffplug.spotless")
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
    apply(plugin = "com.diffplug.spotless")

    dependencies {
        implementation(platform("$quarkusPlatformGroupId:$quarkusPlatformArtifactId:$quarkusPlatformVersion"))
        implementation(platform("$quarkusPlatformGroupId:quarkus-camel-bom:$quarkusPlatformVersion"))
    }

    group = project.group
    version = projectVersion

    spotless {
        kotlin {
            // Target all Kotlin files in the project
            target("**/*.kt", "**/*.kts")
            // Exclude generated files
            targetExclude("**/build/**/*.kt", "**/build/**/*.kts", "**/generated/**")

            // Use ktlint for code formatting
            ktlint("1.0.1")
                .setEditorConfigPath("${rootProject.projectDir.resolve(".editorconfig").absolutePath}")
                .editorConfigOverride(mapOf(
                    "max_line_length" to "140",
                    "ktlint_standard_filename" to "disabled",  // 暂时禁用文件命名规则
                ))

            // Apply additional formatting rules
            trimTrailingWhitespace()
            indentWithSpaces(4)
            endWithNewline()

            // License header configuration
            licenseHeader(
                """
                /*
                 * Copyright 2024 Soybean Admin Backend
                 * Licensed under the Apache License, Version 2.0 (the "License");
                 * you may not use this file except in compliance with the License.
                 */
                """.trimIndent(),
            )
        }
    }

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
