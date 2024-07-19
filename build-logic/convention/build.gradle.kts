plugins {
    `kotlin-dsl`
}

group = "by.g_alex.buildlogic"

repositories {
    google()
    mavenCentral()
    gradlePluginPortal()
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
        register("roomLibrary") {
            id = "todo.roomLib"
            implementationClass = "RoomConventionPlugin"
        }
        register("composeLibrary") {
            id = "todo.composeLib"
            implementationClass = "ComposeConventionPlugin"
        }
        register("divKitLibrary") {
            id = "todo.divKitLib"
            implementationClass = "DivKitConventionPlugin"
        }
        register("validator") {
            id = "validator"
            implementationClass = "by.g_alex.ysmd_todo_compose.tasks.telegram.TelegramReporterPlugin"
        }
        register("telegram-reporter") {
            id = "telegram-reporter"
            implementationClass = "by.g_alex.ysmd_todo_compose.tasks.validate_apk_size.ValidateApkSizePlugin"
        }
    }
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.kotlin.coroutines.core)
    implementation(libs.ktor.client)
    implementation(libs.ktor.client.okhttp)
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}