package api.render

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.response.*
import kotlinx.html.HTML
import kotlinx.html.html
import java.io.StringWriter
import kotlinx.html.stream.appendHTML

fun renderHtml(block: HTML.() -> Unit) = StringWriter().appendHTML().html {
    block()
}.toString()

suspend fun ApplicationCall.respondHtml(
    status: HttpStatusCode? = null,
    configure: OutgoingContent.() -> Unit = {},
    block: HTML.() -> Unit,
) = respondText(
    status = status,
    configure = configure,
    contentType = ContentType.Text.Html,
    text = renderHtml(block)
)
