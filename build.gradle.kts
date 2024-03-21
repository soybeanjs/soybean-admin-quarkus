import org.jetbrains.kotlin.gradle.internal.KaptWithoutKotlincTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    kotlin("plugin.allopen")
    id("io.quarkus")
    id("com.google.devtools.ksp")
    kotlin("kapt")
}

repositories {
    mavenCentral()
    mavenLocal()
}

val quarkusPlatformGroupId: String by project
val quarkusPlatformArtifactId: String by project
val quarkusPlatformVersion: String by project

val projectGroup: String by project
val projectVersion: String by project

val javaVersion = JavaVersion.VERSION_21

val jacksonModuleKotlinVersion = "2.16.1"
val redissonQuarkus30Version = "3.26.1"
val konvertVersion = "3.0.0"
val mutinyReactorVersion = "2.5.6"
val jakartaPersistenceVersion = "3.2.0-M1"

dependencies {
    implementation(enforcedPlatform("${quarkusPlatformGroupId}:${quarkusPlatformArtifactId}:${quarkusPlatformVersion}"))
    implementation("io.quarkus:quarkus-kotlin")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("io.quarkus:quarkus-resteasy-reactive")
    implementation("io.quarkus:quarkus-resteasy-reactive-jackson")
    implementation("io.quarkus:quarkus-hibernate-validator")
    implementation("io.quarkus:quarkus-arc")
    implementation("io.quarkus:quarkus-smallrye-openapi")
    implementation("io.quarkus:quarkus-cache")
    implementation("io.quarkus:quarkus-redis-client")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${jacksonModuleKotlinVersion}")

    implementation("io.quarkus:quarkus-smallrye-jwt")
    implementation("io.quarkus:quarkus-smallrye-jwt-build")
    implementation("io.quarkus:quarkus-elytron-security")
    implementation("org.redisson:redisson-quarkus-30:${redissonQuarkus30Version}")

    implementation("io.quarkus:quarkus-reactive-pg-client")
    implementation("io.quarkus:quarkus-hibernate-reactive-panache-kotlin")

    implementation("io.smallrye.reactive:mutiny-reactor:${mutinyReactorVersion}")

    implementation("io.mcarle:konvert-api:$konvertVersion")
    implementation("io.mcarle:konvert-cdi-annotations:$konvertVersion")

    kapt("jakarta.persistence:jakarta.persistence-api:${jakartaPersistenceVersion}")

    ksp("io.mcarle:konvert:$konvertVersion")
    ksp("io.mcarle:konvert-cdi-injector:$konvertVersion")

    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.rest-assured:rest-assured")
}

configurations {
    all {
        exclude(group = "javax.activation", module = "activation")
        exclude(group = "javax.annotation", module = "javax.annotation-api")
        exclude(group = "javax.cache", module = "cache-api")
//        exclude(group = "javax.inject", module = "javax.inject")
        exclude(group = "javax.xml.bind", module = "jaxb-api")
    }
}

group = projectGroup
version = projectVersion

// 为了兼容 konvert
project.afterEvaluate {
    // 定义要调整依赖的任务名称列表
    val taskNames = listOf("quarkusGenerateCode", "quarkusGenerateCodeDev")

    // 遍历任务名称并调整依赖
    taskNames.forEach { taskName ->
        tasks.named(taskName).configure {
            val newDependsOn = dependsOn
                .filterIsInstance<Provider<Task>>()
                .filterNot { it.get().name == "processResources" }
                .toSet()
            setDependsOn(newDependsOn)
        }
    }
}

java {
    sourceCompatibility = javaVersion
    targetCompatibility = javaVersion
}

kotlin {
    sourceSets.main {
        kotlin.srcDir("build/generated/ksp/main/kotlin")
    }
    sourceSets.test {
        kotlin.srcDir("build/generated/ksp/test/kotlin")
    }
}

tasks.withType<Test> {
    systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
}
allOpen {
    annotation("jakarta.ws.rs.Path")
    annotation("jakarta.enterprise.context.ApplicationScoped")
    annotation("jakarta.persistence.Entity")
    annotation("io.quarkus.test.junit.QuarkusTest")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = javaVersion.toString()
    kotlinOptions.javaParameters = true
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
    options.compilerArgs.add("-Xlint:unchecked")
}

ksp {
    arg("konvert.konverter.generate-class", "true")
}

tasks.withType<KaptWithoutKotlincTask>()
    .configureEach {
        kaptProcessJvmArgs.add("-Xmx512m")
    }