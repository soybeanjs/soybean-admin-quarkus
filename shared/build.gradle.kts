plugins {
    kotlin("jvm")
}

dependencies {
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.fasterxml.jackson.module:jackson-module-parameter-names")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("io.smallrye.reactive:mutiny")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("jakarta.persistence:jakarta.persistence-api")
}