plugins {
    alias(libs.plugins.kotlin.jvm)
}

group = "dev.seano"

version = "1.0-SNAPSHOT"

kotlin {
    jvmToolchain(25)
}
