import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    kotlin("jvm") version "1.5.10"
    application
}

group = "me.olegggatttor"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.slf4j:slf4j-api:+")
    implementation("org.slf4j:slf4j-simple:+")
    implementation("org.mongodb:mongodb-driver-rx:+")
    implementation("io.reactivex:rxnetty-http:+")
    implementation("io.reactivex:rxnetty-common:+")
    implementation("io.reactivex:rxnetty-tcp:+")
    implementation("io.reactivex.rxjava3:rxkotlin:+")
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "11"
}

application {
    mainClass.set("MainKt")
}