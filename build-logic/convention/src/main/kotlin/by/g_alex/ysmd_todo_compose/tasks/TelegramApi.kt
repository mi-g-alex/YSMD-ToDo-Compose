package by.g_alex.ysmd_todo_compose.tasks

import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import java.io.File

private const val BASE_URL = "https://api.telegram.org"

class TelegramApi(
    private val httpClient: HttpClient,
) {

    suspend fun upload(file: File, caption: String = "", token: String, chatId: String): HttpResponse {
        return httpClient.post("$BASE_URL/bot$token/sendDocument") {
            parameter("chat_id", chatId)
            parameter("parse_mode", "HTML")
            if(caption.isNotBlank()) parameter("caption", caption)
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

    suspend fun sendMessage(message: String, token: String, chatId: String): HttpResponse {
        return httpClient.post("$BASE_URL/bot$token/sendMessage") {
            parameter("chat_id", chatId)
            parameter("text", message)
        }
    }
}