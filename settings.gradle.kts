pluginManagement {
    repositories {
        mavenCentral()
        gradlePluginPortal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

rootProject.name = "Microservices"

include ("users")
include ("gateway")
include ("account")
include ("discovery")
//include ("ApiConfigServer")

