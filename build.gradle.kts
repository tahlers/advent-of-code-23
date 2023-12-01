plugins {
    kotlin("jvm") version "1.9.21"
}

group = "me.aoc23"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation("io.kotest:kotest-runner-junit5:5.8.0")
    testImplementation("io.kotest:kotest-assertions-core:5.8.0")
}

tasks.withType<Test>().configureEach {
    useJUnitPlatform()
}
