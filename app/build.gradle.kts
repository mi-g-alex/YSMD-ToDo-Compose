plugins {
    id("todo.mainLib")
    id("todo.hiltLib")
    id("todo.networkLib")
    id("todo.composeLib")
    id("validator")
    id("telegram-reporter")
    alias(libs.plugins.compose.compiler)
}

validateApkSize {
    size = 11 * 1024 * 1024
//    token.set(providers.environmentVariable("TG_TOKEN"))
//    chatId.set(providers.environmentVariable("TG_CHAT"))
    token.set("6999102249:AAEGBclQB7gNtkt1IjD6cqCPSfIeX10FZKI")
    chatId.set("1378305217")
}

telegramReporter {
    checkSize = true
//    token.set(providers.environmentVariable("TG_TOKEN"))
//    chatId.set(providers.environmentVariable("TG_CHAT"))
    token.set("6999102249:AAEGBclQB7gNtkt1IjD6cqCPSfIeX10FZKI")
    chatId.set("1378305217")
}


android {
    namespace = "by.g_alex.ysmd_todo_compose"

    kotlinOptions {
        jvmTarget = "18"
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

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}

kapt {
    correctErrorTypes = true
}