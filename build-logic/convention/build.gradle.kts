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
            id = "todo.mainLib"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("hiltLibrary") {
            id = "todo.hiltLib"
            implementationClass = "HiltConventionPlugin"
        }
        register("networkLibrary") {
            id = "todo.networkLib"
            implementationClass = "NetworkConventionPlugin"
        }

        register("composeLibrary") {
            id = "todo.composeLib"
            implementationClass = "ComposeConventionPlugin"
        }

    }
}