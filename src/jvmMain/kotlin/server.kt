import io.ktor.application.*
import io.ktor.html.*
import io.ktor.http.*
import io.ktor.http.content.*
import io.ktor.routing.*
import kotlinx.html.*
import org.slf4j.LoggerFactory

fun HTML.index() {
    head {
        title("Hello from Ktor!")
    }
    body {
        div {
            +"Hello from Ktor"
        }
        div {
            id = "root"
        }
        script(src = "/static/js.js") {}
    }
}

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
    routing {
        get("/") {
            call.respondHtml(HttpStatusCode.OK, HTML::index)
        }
        static("/static") {
            resources()
        }
    }

    val host = environment.config.propertyOrNull("ktor.deployment.host")?.getString()
    val port = environment.config.propertyOrNull("ktor.deployment.port")?.getString()

    val log = LoggerFactory.getLogger("ktor.application")
    log.info("Server running on ${host}:${port}")
}