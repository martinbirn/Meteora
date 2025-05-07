package org.meteora.presentation.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import coil3.ImageLoader
import coil3.Uri
import coil3.compose.setSingletonImageLoaderFactory
import coil3.network.ktor3.KtorNetworkFetcherFactory
import io.ktor.client.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.compose.koinInject
import org.meteora.logging.AppLogger
import org.meteora.logging.CoilLogger
import org.meteora.presentation.screen.weather.WeatherScreen
import org.meteora.presentation.theme.MeteoraTheme

@Composable
fun App() {
    // init block
    val appLogger = koinInject<AppLogger>()
    key(Unit) {
        appLogger.setup()
    }
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
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF8780A3),
                            Color(0xFFaeaabf)
                        )
                    )
                )
                .systemBarsPadding()
                .navigationBarsPadding()
        ) {
            WeatherScreen()
        }
    }
}