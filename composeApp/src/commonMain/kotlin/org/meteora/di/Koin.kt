package org.meteora.di

import dev.icerock.moko.permissions.PermissionsController
import io.ktor.client.HttpClient
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import org.meteora.data.repository.WeatherRepository
import org.meteora.logging.KtorLogger
import org.meteora.presentation.weather.WeatherViewModel
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
    singleOf(::WeatherRepository)
}

val viewModelModule = module {
    viewModel { (permissionsController: PermissionsController) ->
        WeatherViewModel(permissionsController, weatherRepository = get())
    }
}

fun initKoin() {
    startKoin {
        modules(
            networkModule,
            dataModule,
            viewModelModule,
        )
    }
}
