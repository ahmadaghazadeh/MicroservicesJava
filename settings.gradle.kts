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

//include ("Users")
//include ("apigateway")
//include ("account")
include ("discovery")
//include ("ApiConfigServer")

