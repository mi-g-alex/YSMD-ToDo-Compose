package by.g_alex.ysmd_todo_compose.tasks.validate_apk_size

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

class ValidateApkSizePlugin : Plugin<Project> {

    override fun apply(project: Project) {

        val androidComponents = project.extensions.findByType(AndroidComponentsExtension::class.java)
            ?: throw GradleException("Android not found")

        val extension = project.extensions.create("validateApkSize", ValidateApkSizeExtension::class)

        val telegramApi = TelegramApi(HttpClient(OkHttp))
        androidComponents.onVariants { variant ->
            val artifacts = variant.artifacts.get(SingleArtifact.APK)
            project.tasks.register("ValidateApkSizeFor${variant.name.capitalized()}", ValidateApkSizeTask::class.java, telegramApi).configure {
                apkDir.set(artifacts)
                sizeN.set(extension.size)
            }
        }
    }
}

interface ValidateApkSizeExtension {
    val size: Property<Int>
}