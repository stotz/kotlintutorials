plugins {
    kotlin("jvm") version "2.0.10"
    id("me.champeau.jmh") version "0.6.8"
}

group = "ch.typedef"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.openjdk.jmh:jmh-core:1.37")
    annotationProcessor("org.openjdk.jmh:jmh-generator-annprocess:1.37")
    // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-core
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.9.0")
    // https://mvnrepository.com/artifact/org.openjdk.jmh/jmh-generator-annprocess
    implementation("org.openjdk.jmh:jmh-generator-annprocess:1.37")
    // https://mvnrepository.com/artifact/com.github.albfernandez/juniversalchardet
    implementation("com.github.albfernandez:juniversalchardet:2.5.0")
    // https://mvnrepository.com/artifact/org.openjdk.jmh/jmh-core
    testImplementation("org.openjdk.jmh:jmh-core:1.37")
    testImplementation("org.openjdk.jmh:jmh-generator-annprocess:1.37")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}
