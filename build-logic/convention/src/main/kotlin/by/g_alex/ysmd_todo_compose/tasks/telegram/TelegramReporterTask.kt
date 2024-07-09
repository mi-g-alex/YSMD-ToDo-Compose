package by.g_alex.ysmd_todo_compose.tasks.telegram

import by.g_alex.ysmd_todo_compose.tasks.TelegramApi
import kotlinx.coroutines.runBlocking
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction
import javax.inject.Inject

abstract class TelegramReporterTask @Inject constructor(
    private val telegramApi: TelegramApi
) : DefaultTask() {
    @get:InputDirectory
    abstract val apkDir: DirectoryProperty

    @TaskAction
    fun report() {
        apkDir.get().asFile.listFiles()
            ?.filter { it.name.endsWith(".apk") }
            ?.forEach {
                runBlocking {
                    telegramApi.sendMessage("Build finished").apply {
                        println("Status = $status")
                    }
                }
                runBlocking {
                    telegramApi.upload(it).apply {
                        println("Status = $status")
                    }
                }
            }
    }
}