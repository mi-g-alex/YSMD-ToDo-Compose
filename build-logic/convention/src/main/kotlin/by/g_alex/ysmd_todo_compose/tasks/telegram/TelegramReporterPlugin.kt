package by.g_alex.ysmd_todo_compose.tasks.telegram

import Versions
import Versions.versionCode
import by.g_alex.ysmd_todo_compose.tasks.TelegramApi
import com.android.build.api.artifact.SingleArtifact
import com.android.build.api.variant.AndroidComponentsExtension
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.provider.Property
import org.gradle.configurationcache.extensions.capitalized
import org.gradle.kotlin.dsl.create

class TelegramReporterPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        val androidComponents =
            project.extensions.findByType(AndroidComponentsExtension::class.java)
                ?: throw GradleException("Android not found")

        val extension =
            project.extensions.create("telegramReporter", SendToTelegramExtension::class)

        val telegramApi = TelegramApi(HttpClient(OkHttp))
        androidComponents.onVariants { variant ->
            val artifacts = variant.artifacts.get(SingleArtifact.APK)
            project.tasks.register(
                "reportTelegramApkFor${variant.name.capitalized()}",
                TelegramReporterTask::class.java,
                telegramApi
            ).configure {
                if (extension.checkSize.get())
                    dependsOn("ValidateApkSizeFor${variant.name.capitalized()}")
                apkDir.set(artifacts)
                variantName.set(variant.name.toString())
                versionCode.set(Versions.versionCode.toString())
                token.set(extension.token)
                chatId.set(extension.chatId)
            }
        }
    }
}

interface SendToTelegramExtension {
    val checkSize: Property<Boolean>
    val token: Property<String>
    val chatId: Property<String>
}