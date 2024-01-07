plugins {
    java
}

group = "com.ahmadaghazadeh"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
}

tasks.test {
    useJUnitPlatform()
}

