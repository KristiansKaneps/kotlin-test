package api

import kotlinx.browser.window
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.await
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.w3c.fetch.RequestInit
import org.w3c.fetch.Response
import kotlin.js.Promise
import kotlin.js.json

// Used throughout to wrap API calls and effects.
val mainScope = MainScope()

private suspend fun Promise<Response>.assertStatus() = await().apply {
    status.toInt().also {
        check(200 == it || 0 == it) {
            "Operation failed: $status  $url".also { msg ->
                console.log(msg)
                window.alert(msg)
            }
        }
    }
}

suspend fun fetch(method: String, url: String, body: dynamic = null): Response =
    window.fetch(
        url, RequestInit(
            method = method,
            body = body,
            headers = json(
                "Content-Type" to "application/json",
                "Accept" to "application/json",
                "pragma" to "no-cache"
            )
        )
    ).assertStatus()

suspend fun get(url: String): Response =
    fetch("GET", url)

suspend fun put(url: String, body: dynamic): Response =
    fetch("PUT", url, JSON.stringify(body))

suspend fun post(url: String, body: dynamic): Response =
    fetch("POST", url, JSON.stringify(body))

suspend fun delete(url: String): Response =
    fetch("DELETE", url)

/**
 * Serialize object from JSON in response.
 */
suspend inline fun <reified T> json(response: Response): T =
    Json.decodeFromString(response.text().await())
