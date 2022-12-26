package com.checkout.infrastructure.database

import com.github.michaelbull.logging.InlineLogger
import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.Database
import java.util.Properties

internal class HikariConnector (
    private val databaseConfig: Properties,
) : DBConnector {
    private val logger = InlineLogger()
    private lateinit var ds: HikariDataSource
    private lateinit var db: Database

    override suspend fun <T> bootStorage(preInit: suspend () -> T) {
        logger.info { "Initializing database" }
        ds = HikariDataSource(HikariConfig(databaseConfig))
        db = Database.connect(ds)
    }

    override fun shutdownStorage() {
        if(this::ds.isInitialized) {
            ds.close()
        } else {
            logger.warn { "Request to close datasource was ignored, it was already closed" }
        }
    }
}

interface DBConnector {
    suspend fun <T> bootStorage(preInit: suspend () -> T)
    fun shutdownStorage()
}