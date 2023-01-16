package api.routes

import api.render.respondHtml
import io.ktor.application.*
import io.ktor.http.content.*
import io.ktor.routing.*
import io.micronaut.ktor.KtorRoutingBuilder
import jakarta.inject.Singleton
import kotlinx.html.*

@Singleton
class HomeRoute : KtorRoutingBuilder({
    get("/") {
        call.respondHtml {
            lang = "en"
            head {
                meta { charset = "utf-8" }
                title { +"Home" }
            }
            body {
                noScript {
                    +"You need to enable JavaScript to run this app."
                }
                div {
                    id = "root"
                    +"Loading..."
                }
                script(src = "app.js") {}
            }
        }
    }

    static {
        resources("/")
    }
})
