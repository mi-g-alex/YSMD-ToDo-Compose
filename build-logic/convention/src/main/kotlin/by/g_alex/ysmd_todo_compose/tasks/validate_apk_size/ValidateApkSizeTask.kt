package by.g_alex.ysmd_todo_compose.tasks.validate_apk_size

import by.g_alex.ysmd_todo_compose.tasks.TelegramApi
import kotlinx.coroutines.runBlocking
import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction
import javax.inject.Inject

abstract class ValidateApkSizeTask @Inject constructor(
    private val telegramApi: TelegramApi
) : DefaultTask() {
    @get:InputDirectory
    abstract val apkDir: DirectoryProperty

    @get:Input
    abstract val sizeN: Property<Int>

    @TaskAction
    fun report() {
        apkDir.get().asFile.listFiles()
            ?.filter { it.name.endsWith(".apk") }
            ?.forEach {
                if (it.length() > sizeN.get()) {
                    runBlocking {
                        telegramApi.sendMessage("${it.name} lager then ${sizeN.get()} bytes")
                            .apply {
                                throw GradleException("${it.name} lager then ${sizeN.get()} bytes")
                            }
                    }
                }
            }
    }
}