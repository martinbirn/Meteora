package org.meteora.di

import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module
import org.meteora.data.repository.WeatherApiRepositoryImpl
import org.meteora.data.repository.WeatherLocalRepositoryImpl
import org.meteora.domain.entity.LocationInfo
import org.meteora.domain.repository.WeatherApiRepository
import org.meteora.domain.repository.WeatherLocalRepository
import org.meteora.logging.AppLogger
import org.meteora.logging.KtorLogger
import org.meteora.presentation.screen.locations.LocationsViewModel
import org.meteora.presentation.screen.locationsearch.LocationSearchViewModel
import org.meteora.presentation.screen.locationweather.LocationWeatherViewModel
import org.meteora.util.MeteoraJson

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
    single<WeatherApiRepository> { WeatherApiRepositoryImpl(client = get()) }
    single<WeatherLocalRepository> { WeatherLocalRepositoryImpl() }
}

val viewModelModule = module {
    viewModel { (locationInfo: LocationInfo) ->
        LocationWeatherViewModel(locationInfo, weatherApiRepository = get())
    }
    viewModelOf(::LocationsViewModel)
    viewModelOf(::LocationSearchViewModel)
}

val utilsModule = module {
    singleOf(::AppLogger)
}

fun initKoin() {
    startKoin {
        modules(allModules)
    }
}

val KoinApplication.allModules: List<Module>
    get() = listOf(networkModule, dataModule, viewModelModule, utilsModule)
