package org.meteora

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import coil3.ImageLoader
import coil3.Uri
import coil3.compose.setSingletonImageLoaderFactory
import coil3.network.ktor3.KtorNetworkFetcherFactory
import io.ktor.client.HttpClient
import kotlinx.coroutines.Dispatchers
import org.koin.compose.koinInject
import org.meteora.logging.CoilLogger
import org.meteora.presentation.screens.weather.WeatherScreen
import org.meteora.presentation.theme.MeteoraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val httpClient = koinInject<HttpClient>()
            setSingletonImageLoaderFactory { context ->
                ImageLoader.Builder(context)
                    .coroutineContext(Dispatchers.IO)
                    .components {
                        addFetcherFactories {
                            listOf(
                                KtorNetworkFetcherFactory(httpClient) to Uri::class
                            )
                        }
                    }
                    .apply {
                        logger(CoilLogger())
                    }
                    .build()
            }

            MeteoraTheme {
                WeatherScreen()
            }
        }
    }
}