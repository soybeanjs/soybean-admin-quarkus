import org.jetbrains.kotlin.gradle.internal.KaptWithoutKotlincTask
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm")
    kotlin("plugin.allopen")
    kotlin("kapt")
    id("io.quarkus")
    id("com.google.devtools.ksp")
}

val javaVersion = JavaVersion.VERSION_21

val jacksonModuleKotlinVersion = "2.17.0"
val redissonQuarkus30Version = "3.27.2"
val konvertVersion = "3.0.1"
val mutinyReactorVersion = "2.6.0"
val idgeneratorVersion = "1.0.6"
val jakartaPersistenceVersion = "3.2.0-M2"

dependencies {
    implementation("jakarta.persistence:jakarta.persistence-api")
    implementation("com.github.yitter:yitter-idgenerator:${idgeneratorVersion}")

    implementation("io.quarkus:quarkus-kubernetes")
    implementation("io.quarkus:quarkus-container-image-jib")
    implementation("io.quarkus:quarkus-smallrye-health")

    implementation("io.quarkus:quarkus-reactive-pg-client")
    implementation("io.quarkus:quarkus-smallrye-graphql")

    implementation("io.quarkus:quarkus-arc")
    implementation("io.quarkus:quarkus-vertx")
    implementation("io.quarkus:quarkus-mongodb-panache-kotlin")
    implementation("io.quarkus:quarkus-hibernate-reactive-panache-kotlin")
    implementation("io.quarkus:quarkus-smallrye-reactive-messaging-kafka")

    implementation("io.quarkus:quarkus-smallrye-openapi")
    implementation("io.quarkus:quarkus-opentelemetry")
    implementation("io.quarkus:quarkus-smallrye-fault-tolerance")

    implementation("io.quarkus:quarkus-elytron-security")
    implementation("io.quarkus:quarkus-smallrye-jwt")
    implementation("io.quarkus:quarkus-smallrye-jwt-build")

    implementation("io.quarkus:quarkus-hibernate-validator")
    implementation("io.quarkus:quarkus-rest")
    implementation("io.quarkus:quarkus-rest-jackson")

    implementation("io.quarkus:quarkus-cache")
    implementation("io.quarkus:quarkus-redis-client")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${jacksonModuleKotlinVersion}")
    implementation("org.redisson:redisson-quarkus-30:${redissonQuarkus30Version}")

    implementation(project(":shared"))

    implementation("io.smallrye.reactive:mutiny-reactor:${mutinyReactorVersion}")
    implementation("io.smallrye.reactive:mutiny-kotlin:${mutinyReactorVersion}")

    implementation("io.mcarle:konvert-api:$konvertVersion")
    implementation("io.mcarle:konvert-cdi-annotations:$konvertVersion")

    ksp("io.mcarle:konvert:$konvertVersion")
    ksp("io.mcarle:konvert-cdi-injector:$konvertVersion")

    kapt("jakarta.persistence:jakarta.persistence-api:${jakartaPersistenceVersion}")

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

allOpen {
    annotation("jakarta.ws.rs.Path")
    annotation("jakarta.enterprise.context.ApplicationScoped")
    annotation("jakarta.persistence.Entity")
    annotation("io.quarkus.test.junit.QuarkusTest")
}

kotlin {
    sourceSets.main {
        kotlin.srcDir("build/generated/ksp/main/kotlin")
    }
    sourceSets.test {
        kotlin.srcDir("build/generated/ksp/test/kotlin")
    }
}

ksp {
    arg("konvert.konverter.generate-class", "true")
}

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

tasks.withType<Test> {
    systemProperty("java.util.logging.manager", "org.jboss.logmanager.LogManager")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
    options.compilerArgs.add("-Xlint:unchecked")
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = javaVersion.toString()
    kotlinOptions.javaParameters = true
}

tasks.withType<KaptWithoutKotlincTask>()
    .configureEach {
        kaptProcessJvmArgs.add("-Xmx512m")
    }