package com.checkout.plugins

import com.checkout.web.healthRoute
import com.github.michaelbull.logging.InlineLogger
import io.ktor.server.routing.*
import io.ktor.server.application.*
import io.ktor.server.response.*

private val logger = InlineLogger()
fun Application.configureRouting() {
    routing {
        trace { logger.debug { "routing/trace(): ${it.buildText()}" } }

        get("/") {
            call.respondText("Hello World!")
        }
        healthRoute()
    }
}
