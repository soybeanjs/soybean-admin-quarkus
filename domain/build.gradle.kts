import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinVersion
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    alias(libs.plugins.kotlin)
}

val javaVersion = JavaVersion.VERSION_21

dependencies {
    implementation(project(":shared"))

    implementation("io.smallrye.reactive:mutiny")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.17.2")
    implementation("io.quarkus:quarkus-panache-common")
    implementation("io.quarkus:quarkus-hibernate-reactive-panache-kotlin")

    implementation("io.quarkus:quarkus-elytron-security-common")
    implementation("org.eclipse.microprofile.openapi:microprofile-openapi-api")
}

java {
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        apiVersion = KotlinVersion.KOTLIN_1_9
        languageVersion = KotlinVersion.KOTLIN_2_0

        jvmTarget = JvmTarget.JVM_21
        // w: Scripts are not yet supported with K2 in LightTree mode, consider using K1 or disable LightTree mode with -Xuse-fir-lt=false
        freeCompilerArgs.add("-Xuse-fir-lt=false")
    }
}