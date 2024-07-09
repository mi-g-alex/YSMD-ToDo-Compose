package by.g_alex.ysmd_todo_compose.tasks

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import java.io.File

private const val BASE_URL = "https://api.telegram.org"
private const val TOKEN = "6999102249:AAEGBclQB7gNtkt1IjD6cqCPSfIeX10FZKI"
private const val CHAT_ID = "1378305217"

class TelegramApi(
    private val httpClient: HttpClient,
) {

    suspend fun upload(file: File): HttpResponse {
        return httpClient.post("$BASE_URL/bot$TOKEN/sendDocument") {
            parameter("chat_id", CHAT_ID)
            setBody(
                MultiPartFormDataContent(
                    formData {
                        append("document", file.readBytes(), Headers.build {
                            append(
                                HttpHeaders.ContentDisposition,
                                "filename=${file.name.escapeIfNeeded()}"
                            )
                        })
                    }
                )
            )
        }
    }

    suspend fun sendMessage(message: String): HttpResponse {
        return httpClient.post("$BASE_URL/bot$TOKEN/sendMessage") {
            parameter("chat_id", CHAT_ID)
            parameter("text", message)
        }
    }
}