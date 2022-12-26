package com.checkout.di

import com.checkout.database.DBConnector
import com.checkout.database.HikariConnector
import com.checkout.env.AppOsEnvironment
import com.checkout.env.Env
import com.checkout.env.HoconBasedConfig
import org.koin.dsl.module

val envModule = module {
    single<Env> {HoconBasedConfig(AppOsEnvironment())}
}

val persistenceModule = module {
    single<DBConnector> { HikariConnector(databaseConfig = get<Env>().database)}
}
