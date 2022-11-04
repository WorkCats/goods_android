plugins {
    id("com.google.devtools.ksp") version "1.7.20-1.0.8"
}

buildscript {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    val gradleVersion = "8.0.0-alpha07"
    val kotlinVersion = "1.7.20"

    dependencies {
        classpath("com.android.tools.build:gradle:$gradleVersion")
        classpath(kotlin("gradle-plugin", version = kotlinVersion))
    }
}

tasks.register<Delete>("clean").configure {
    delete(rootProject.buildDir)
}