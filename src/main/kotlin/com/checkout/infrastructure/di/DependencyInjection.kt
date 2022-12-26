package com.checkout.infrastructure.di

import com.checkout.infrastructure.database.DBConnector
import com.checkout.infrastructure.database.HikariConnector
import com.checkout.infrastructure.env.AppOsEnvironment
import com.checkout.infrastructure.env.Env
import com.checkout.infrastructure.env.HoconBasedConfig
import org.koin.dsl.module

val envModule = module {
    single<Env> { HoconBasedConfig(AppOsEnvironment()) }
}

val persistenceModule = module {
    single<DBConnector> { HikariConnector(databaseConfig = get<Env>().database) }
}
