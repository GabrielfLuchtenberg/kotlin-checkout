package com.checkout.plugins

import com.checkout.di.envModule
import com.checkout.di.persistenceModule
import io.ktor.server.application.*
import org.koin.ktor.plugin.Koin
import org.koin.logger.slf4jLogger

fun Application.configureDI() {
    install(Koin) {
        slf4jLogger()
        modules(envModule,
            persistenceModule
        )
    }
}