plugins {
    id("todo.mainLib")
    id("todo.hiltLib")
    id("todo.networkLib")
    id("todo.composeLib")
    id("todo.roomLib")
    id("todo.divKitLib")
    id("validator")
    id("telegram-reporter")
    alias(libs.plugins.compose.compiler)
}

validateApkSize {
    size = 20 * 1024 * 1024
    token.set(providers.environmentVariable("TG_TOKEN"))
    chatId.set(providers.environmentVariable("TG_CHAT"))
}

telegramReporter {
    checkSize = false
    token.set(providers.environmentVariable("TG_TOKEN"))
    chatId.set(providers.environmentVariable("TG_CHAT"))
}


android {
    namespace = "by.g_alex.ysmd_todo_compose"

    kotlinOptions {
        jvmTarget = "18"
    }

    ksp {
        arg("room.schemaLocation", "$projectDir/schemas")
    }

    flavorDimensions += "plan"
    productFlavors {
        create("basic") {
            dimension = "plan"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.coordinatorlayout)
    implementation(libs.material)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}