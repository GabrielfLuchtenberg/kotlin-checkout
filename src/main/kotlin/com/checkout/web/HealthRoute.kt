package com.checkout.web

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.*


@Serializable
data class RestHealthResponse (
    val status: String,
    val remoteService: String,
    val database: String
)

internal fun Routing.healthRoute() {
    get("/health") {
        call.respond(
            status = HttpStatusCode.OK,
            message = RestHealthResponse(
                status = "success",
                remoteService = "success",
                database = "success",
            )
        )
    }
}