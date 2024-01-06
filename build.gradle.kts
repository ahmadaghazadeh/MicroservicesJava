plugins {
    kotlin("jvm") version "1.8.22"
}

group = "com.ahmadaghazadeh"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(17)
}