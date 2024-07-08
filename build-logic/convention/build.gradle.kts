import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "by.g_alex.buildlogic"

repositories {
    google()
    mavenCentral()
}

java {
    sourceCompatibility = JavaVersion.VERSION_18
    targetCompatibility = JavaVersion.VERSION_18
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_18
    }
}


dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

gradlePlugin {
    plugins {
        register("androidLibrary") {
            id = "g_alex.adnroid.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
    }
}