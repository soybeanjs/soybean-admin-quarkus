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

val jacksonModuleKotlinVersion = "2.17.1"
val redissonQuarkus30Version = "3.29.0"
val konvertVersion = "3.2.0"
val mutinyReactorVersion = "2.6.0"
val idgeneratorVersion = "1.0.6"
val jakartaPersistenceVersion = "3.2.0-M2"
val jnrUnixsocketVersion = "0.38.22"
val ip2regionVersion = "2.7.0"
val msgpackVersion = "0.9.8"

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
//    implementation("io.quarkus:quarkus-smallrye-reactive-messaging-kafka")
    implementation("io.quarkus:quarkus-messaging-kafka")

    implementation("io.quarkus:quarkus-smallrye-openapi")
    implementation("io.quarkus:quarkus-opentelemetry")
    implementation("io.quarkus:quarkus-smallrye-fault-tolerance")

    implementation("io.quarkus:quarkus-elytron-security")
    implementation("io.quarkus:quarkus-smallrye-jwt")
    implementation("io.quarkus:quarkus-smallrye-jwt-build")

    implementation("io.quarkus:quarkus-hibernate-validator")
    implementation("io.quarkus:quarkus-rest")
    implementation("io.quarkus:quarkus-rest-jackson")
    implementation("org.lionsoul:ip2region:${ip2regionVersion}")

    implementation("org.msgpack:jackson-dataformat-msgpack:${msgpackVersion}")

    implementation("io.quarkus:quarkus-cache")
    implementation("io.quarkus:quarkus-redis-client")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:${jacksonModuleKotlinVersion}")
    implementation("org.redisson:redisson-quarkus-30:${redissonQuarkus30Version}")

    implementation(project(":shared"))

    //tips 2024-04-17 quarkus3.9.*如果需要native image 需要移除这两个依赖或者降级到2.5.8或者开启下方resolutionStrategy
    //tips 另外除非需要一些响应式类库的互转比如reactor的flux和mono等或者kt协程等一般不需要smallrye转换类库
//    implementation("io.smallrye.reactive:mutiny-reactor:${mutinyReactorVersion}")
//    implementation("io.smallrye.reactive:mutiny-kotlin:${mutinyReactorVersion}")

    implementation("io.mcarle:konvert-api:$konvertVersion")
    implementation("io.mcarle:konvert-cdi-annotations:$konvertVersion")

    //tips 2024-04-17 此依赖解决mongo底层jnr native编译问题 此外有另一种方案未采用 自行搜索相关issue
    implementation("com.github.jnr:jnr-unixsocket:${jnrUnixsocketVersion}")

    implementation("org.apache.camel.quarkus:camel-quarkus-reactive-executor")
    implementation("org.apache.camel.quarkus:camel-quarkus-reactive-streams")
    implementation("org.apache.camel.quarkus:camel-quarkus-direct")
    implementation("org.apache.camel.quarkus:camel-quarkus-yaml-dsl")
    implementation("org.apache.camel.quarkus:camel-quarkus-kotlin-dsl")
    implementation("org.apache.camel.quarkus:camel-quarkus-bean")
    implementation("org.apache.camel.quarkus:camel-quarkus-rest")
    implementation("org.apache.camel.quarkus:camel-quarkus-platform-http")
    implementation("org.apache.camel.quarkus:camel-quarkus-caffeine")
    implementation("org.apache.camel.quarkus:camel-quarkus-disruptor")
    implementation("org.apache.camel.quarkus:camel-quarkus-timer")

    ksp("io.mcarle:konvert:$konvertVersion")
    ksp("io.mcarle:konvert-cdi-injector:$konvertVersion")

    kapt("jakarta.persistence:jakarta.persistence-api:${jakartaPersistenceVersion}")

    testImplementation("io.quarkus:quarkus-junit5")
    testImplementation("io.rest-assured:rest-assured")
}

configurations.all {
    exclude(group = "javax.activation", module = "activation")
    exclude(group = "javax.annotation", module = "javax.annotation-api")
    exclude(group = "javax.cache", module = "cache-api")
//        exclude(group = "javax.inject", module = "javax.inject")
    exclude(group = "javax.xml.bind", module = "jaxb-api")

    //tips 2024-04-17 quarkus3.9.*如果需要native image 需要强制升级以下依赖或者使用上方依赖降级方案
//    resolutionStrategy {
//        // 强制使用特定版本的依赖
//        force("io.smallrye.reactive:smallrye-reactive-messaging-provider:4.21.0")
//        force("io.smallrye.reactive:smallrye-reactive-messaging-kafka:4.21.0")
//
//        eachDependency {
//            if (requested.group == "io.smallrye.reactive" && requested.name.startsWith("smallrye-reactive-messaging")) {
//                useVersion("4.21.0")
//            }
//        }
//    }
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
