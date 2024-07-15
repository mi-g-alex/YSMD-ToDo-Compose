package by.g_alex.ysmd_todo_compose.tasks.telegram

import by.g_alex.ysmd_todo_compose.tasks.TelegramApi
import kotlinx.coroutines.runBlocking
import org.gradle.api.DefaultTask
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction
import java.io.File
import java.io.FileInputStream
import java.io.PrintWriter
import java.util.Calendar
import java.util.Locale
import java.util.zip.ZipInputStream
import javax.inject.Inject


abstract class TelegramReporterTask @Inject constructor(
    private val telegramApi: TelegramApi
) : DefaultTask() {
    @get:InputDirectory
    abstract val apkDir: DirectoryProperty

    @get:Input
    abstract val variantName: Property<String>

    @get:Input
    abstract val versionCode: Property<String>

    @get:Input
    abstract val token: Property<String>

    @get:Input
    abstract val chatId: Property<String>

    @TaskAction
    fun report() {
        println("Files: " + apkDir.get().asFileTree.asPath)
        apkDir.get().asFileTree.toList()
            .filter { it.name.endsWith(".apk") }
            .forEach {

                val newName = "${it.parent}/todolist-${variantName.get()}-${versionCode.get()}.apk"
                val nFile = File(newName)
                it.renameTo(nFile)

                val caption = createReport(unzip(nFile))

                val fileReport = File("report-${Calendar.getInstance().time.time}.html")
                val writer = PrintWriter(fileReport, "UTF-8")
                writer.println(caption)
                writer.close()

                runBlocking {
                    telegramApi.sendMessage("Build finished", token.get(), chatId.get()).apply {
                        println("Status = $status")
                    }
                }

                runBlocking {
                    telegramApi.upload(fileReport, token = token.get(), chatId = chatId.get())
                        .apply {
                            println("Status = $status")
                            fileReport.delete()
                        }
                }

                runBlocking {
                    telegramApi.upload(
                        nFile,
                        nFile.length().toFileSize(),
                        token.get(),
                        chatId.get()
                    ).apply {
                        println("Status = $status")
                    }
                }
            }
    }

    data class UnzippedFile(val filename: String, val size: Long, val path: String)

    private fun unzip(file: File): List<UnzippedFile> = ZipInputStream(FileInputStream(file))
        .use { zipInputStream ->
            generateSequence { zipInputStream.nextEntry }
                .filterNot { it.isDirectory }
                .map {
                    val fn = it.name.split('/').last()
                    UnzippedFile(
                        filename = fn,
                        size = it.size,
                        path = it.name.removeSuffix(fn)
                    )
                }.toList()
        }


    private fun createReport(files: List<UnzippedFile>): String {
        var oldPath = ""
        var report = "<h1>Report: </h1><br>" +
                "<h4>Root:</h4><ul>"
        files.sortedBy { it.path }.forEach {
            if (it.path != oldPath) {
                if (it.path != "") report += "</ul><br><br><h4>" + it.path + "</h4><ul>"
                oldPath = it.path
            }
            report += "<li>${it.filename}<br>${it.size.toFileSize()}</li>"
        }
        report += "</ul>"
        return report
    }

    private fun Long.toFileSize(): String =
        when (this) {
            in (0L until 1024L) -> {
                "${
                    String.format(Locale.US, "%.2f", this.toDouble())
                        .removeSuffix(".00")
                        .removeSuffix("0")
                }B"
            }

            in (1024L until 1024L * 1024L) -> {
                "${
                    String.format(Locale.US, "%.2f", (this.toDouble() / 1024L))
                        .removeSuffix(".00")
                        .removeSuffix("0")
                }KB"
            }

            else -> {
                "${
                    String.format(Locale.US, "%.2f", (this.toDouble() / 1024L / 1024L))
                        .removeSuffix(".00")
                        .removeSuffix("0")
                }MB"
            }
        }
}
