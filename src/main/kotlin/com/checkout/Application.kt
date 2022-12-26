package com.checkout

import com.checkout.infrastructure.database.DBConnector
import io.ktor.server.application.*
import com.checkout.plugins.*
import com.github.michaelbull.logging.InlineLogger
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.contentnegotiation.*
import kotlinx.coroutines.runBlocking
import org.koin.ktor.ext.inject

private val logger = InlineLogger()

fun main(args: Array<String>): Unit =
    io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused") // application.conf references the main function. This annotation prevents the IDE from marking it as unused.
fun Application.module() {
    install(ContentNegotiation) {
        json()
    }
    configureDI()


    val storage by inject<DBConnector>()

    environment.monitor.subscribe(ApplicationStopped) {
        logger.info {"Server being shutdown"}
        storage.shutdownStorage()
    }

    runBlocking {
        storage.bootStorage { }
    }
    configureRouting()
}
