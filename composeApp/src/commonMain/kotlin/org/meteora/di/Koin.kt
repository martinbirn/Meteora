package org.meteora.di

import dev.icerock.moko.geo.LocationTracker
import dev.icerock.moko.permissions.PermissionsController
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.meteora.data.repository.WeatherRepositoryImpl
import org.meteora.domain.repository.WeatherRepository
import org.meteora.logging.KtorLogger
import org.meteora.presentation.screens.weather.WeatherViewModel
import org.meteora.utils.MeteoraJson

val networkModule = module {
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
            /*defaultRequest {
                url("https://api.openweathermap.org/data/2.5")
            }*/
        }
    }
}

val dataModule = module {
    single<WeatherRepository> { WeatherRepositoryImpl(client = get()) }
}

val viewModelModule = module {
    viewModel { (locationTracker: LocationTracker, permissionsController: PermissionsController) ->
        WeatherViewModel(locationTracker, permissionsController, weatherRepository = get())
    }
}

fun initKoin() {
    startKoin {
        modules(allModules)
    }
}

val KoinApplication.allModules: List<Module>
    get() = listOf(networkModule, dataModule, viewModelModule)
