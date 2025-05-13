package org.meteora.presentation.screen

import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.IntOffset
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import coil3.ImageLoader
import coil3.Uri
import coil3.compose.setSingletonImageLoaderFactory
import coil3.network.ktor3.KtorNetworkFetcherFactory
import io.ktor.client.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.compose.koinInject
import org.meteora.domain.entity.LocationInfo
import org.meteora.domain.entity.LocationInfoNavType
import org.meteora.logging.AppLogger
import org.meteora.logging.CoilLogger
import org.meteora.presentation.screen.locations.LocationsScreen
import org.meteora.presentation.screen.locationsearch.LocationSearchScreen
import org.meteora.presentation.screen.locationweather.LocationWeatherScreen
import org.meteora.presentation.theme.MeteoraTheme
import kotlin.reflect.typeOf

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
                val animationSpec = tween<IntOffset>(700)
                val keyboardController = LocalSoftwareKeyboardController.current
                NavHost(
                    navController = navController,
                    startDestination = LocationsDestination,
                    modifier = Modifier.fillMaxSize(),
                    enterTransition = {
                        slideIntoContainer(SlideDirection.Left, animationSpec)
                    },
                    exitTransition = {
                        slideOutOfContainer(SlideDirection.Left, animationSpec)
                    },
                    popEnterTransition = {
                        slideIntoContainer(SlideDirection.Right, animationSpec)
                    },
                    popExitTransition = {
                        slideOutOfContainer(SlideDirection.Right, animationSpec)
                    }
                ) {
                    composable<LocationsDestination> {
                        LocationsScreen(
                            sharedTransitionScope = this@SharedTransitionLayout,
                            animatedContentScope = this@composable,
                            navigateToLocationSearch = {
                                keyboardController?.hide()
                                navController.navigate(SearchLocationDestination)
                            }
                        )
                    }
                    composable<LocationWeatherDestination>(
                        typeMap = mapOf(typeOf<LocationInfo>() to LocationInfoNavType)
                    ) { backStackEntry ->
                        val route = backStackEntry.toRoute<LocationWeatherDestination>()
                        LocationWeatherScreen(locationInfo = route.locationInfo)
                    }
                    composable<SearchLocationDestination> {
                        LocationSearchScreen(
                            sharedTransitionScope = this@SharedTransitionLayout,
                            animatedContentScope = this@composable,
                            onLocationClicked = { locationInfo ->
                                keyboardController?.hide()
                                navController.navigate(
                                    route = LocationWeatherDestination(locationInfo = locationInfo)
                                )
                            },
                            navigateBack = {
                                keyboardController?.hide()
                                navController.popBackStack()
                            }
                        )
                    }
                }
            }
        }
    }
}