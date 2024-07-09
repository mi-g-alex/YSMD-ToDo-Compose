import java.net.HttpURLConnection
import java.net.URL
import java.io.File
import java.io.OutputStreamWriter
import java.io.BufferedWriter
import java.nio.file.Files
import java.nio.file.Paths
import org.apache.commons.io.IOUtils

//
//val helloTask = tasks.register("hello", Telegram) {
//
//}

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

/*
val telegramToken = "6999102249:AAEGBclQB7gNtkt1IjD6cqCPSfIeX10FZKI"
val chatId = "1378305217"

tasks.register("sendApkToTelegram") {
    doLast {
        val apkFile = file("$buildDir/outputs/apk/release/app-release-unsigned.apk")

        if (apkFile.exists()) {
            val url = URL("https://api.telegram.org/bot$telegramToken/sendDocument")
            val boundary = "----WebKitFormBoundary" + System.currentTimeMillis()
            val lineEnd = "\r\n"
            val twoHyphens = "--"
            val mimeType = "application/octet-stream"

            val connection = url.openConnection() as HttpURLConnection
            connection.doOutput = true
            connection.useCaches = false
            connection.requestMethod = "POST"
            connection.setRequestProperty("Connection", "Keep-Alive")
            connection.setRequestProperty("Content-Type", "multipart/form-data; boundary=$boundary")

            val outputStream = connection.outputStream
            val writer = BufferedWriter(OutputStreamWriter(outputStream, "UTF-8"))

            writer.append(twoHyphens + boundary + lineEnd)
            writer.append("Content-Disposition: form-data; name=\"chat_id\"$lineEnd")
            writer.append(lineEnd)
            writer.append(chatId)
            writer.append(lineEnd)

            writer.append(twoHyphens + boundary + lineEnd)
            writer.append("Content-Disposition: form-data; name=\"document\"; filename=\"" + apkFile.name + "\"$lineEnd")
            writer.append("Content-Type: $mimeType$lineEnd")
            writer.append(lineEnd)
            writer.flush()

            val fileInputStream = Files.newInputStream(Paths.get(apkFile.toURI()))
            IOUtils.copy(fileInputStream, outputStream)
            outputStream.flush()
            fileInputStream.close()

            writer.append(lineEnd)
            writer.append(twoHyphens + boundary + twoHyphens + lineEnd)
            writer.flush()
            writer.close()
            outputStream.close()

            val response = connection.inputStream.bufferedReader().use { it.readText() }
            println("Response: $response")
        } else {
            println("APK file not found: $apkFile")
        }
    }
}

// Example of how to hook this task after the assemble task
tasks.whenTaskAdded {
    finalizedBy("sendApkToTelegram")
}*/
