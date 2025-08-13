package org.meteora.di

import app.cash.sqldelight.db.SqlDriver
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.module
import org.meteora.cache.Database
import org.meteora.cache.DatabaseDriverFactory
import org.meteora.data.repository.WeatherApiRepositoryImpl
import org.meteora.data.repository.WeatherLocalRepositoryImpl
import org.meteora.domain.repository.WeatherApiRepository
import org.meteora.domain.repository.WeatherLocalRepository
import org.meteora.logging.AppLogger
import org.meteora.logging.KtorLogger
import org.meteora.util.MeteoraJson

expect fun platformModule(): Module

fun initKoin(appDeclaration: KoinAppDeclaration = {}) =
    startKoin {
        modules(
            platformModule(),
            networkModule(),
            dataModule(),
            utilsModule()
        )
        appDeclaration()
    }

// called by iOS client
fun initKoin() = initKoin {}

fun networkModule() = module {
    single {
        HttpClient {
            install(ContentNegotiation) {
                json(MeteoraJson)
            }
            install(HttpTimeout) {
                requestTimeoutMillis = 30_000
                socketTimeoutMillis = 30_000
            }
            install(Logging) {
                logger = KtorLogger()
                level = LogLevel.ALL
            }
        }
    }
}

fun dataModule() = module {
    single<SqlDriver> { get<DatabaseDriverFactory>().createDriver() }
    single<Database> { Database(sqlDriver = get()) }

    single<WeatherApiRepository> { WeatherApiRepositoryImpl(client = get()) }
    single<WeatherLocalRepository> { WeatherLocalRepositoryImpl(database = get()) }
}

fun utilsModule() = module {
    singleOf(::AppLogger)
}
