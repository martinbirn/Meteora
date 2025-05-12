package org.meteora.presentation.screen

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
import org.meteora.presentation.screen.locations.LocationsScreen
import org.meteora.presentation.screen.locationsearch.LocationSearchScreen
import org.meteora.presentation.screen.locationweather.LocationWeatherScreen
import org.meteora.presentation.theme.MeteoraTheme

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun App() {
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
                //.shaderEffect(ShaderOptions.SNOW)
                .systemBarsPadding()
                .navigationBarsPadding()
        ) {
            SharedTransitionLayout {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = WeatherScreen.Locations.name,
                    modifier = Modifier.fillMaxSize()
                ) {
                    composable(route = WeatherScreen.Locations.name) {
                        LocationsScreen(
                            sharedTransitionScope = this@SharedTransitionLayout,
                            animatedContentScope = this@composable,
                            navigateToLocationSearch = {
                                navController.navigate(WeatherScreen.SearchLocation.name)
                            }
                        )
                    }
                    composable(route = WeatherScreen.LocationWeather.name) {
                        LocationWeatherScreen()
                    }
                    composable(route = WeatherScreen.SearchLocation.name) {
                        LocationSearchScreen(
                            sharedTransitionScope = this@SharedTransitionLayout,
                            animatedContentScope = this@composable,
                            navigateBack = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}