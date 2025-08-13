package org.meteora.presentation.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.key
import coil3.ImageLoader
import coil3.Uri
import coil3.compose.setSingletonImageLoaderFactory
import coil3.network.ktor3.KtorNetworkFetcherFactory
import com.arkivanov.decompose.extensions.compose.experimental.stack.ChildStack
import dev.chrisbanes.haze.rememberHazeState
import io.ktor.client.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.compose.koinInject
import org.meteora.logging.AppLogger
import org.meteora.logging.CoilLogger
import org.meteora.presentation.decompose.AppComponent
import org.meteora.presentation.decompose.AppComponent.Child
import org.meteora.presentation.decompose.DefaultAppComponent
import org.meteora.presentation.screen.locations.LocationsScreen
import org.meteora.presentation.screen.locationsearch.LocationSearchScreen
import org.meteora.presentation.screen.locationweather.LocationWeatherScreen
import org.meteora.presentation.screen.locationweather.LocationWeatherSheet
import org.meteora.presentation.theme.MeteoraTheme
import org.meteora.presentation.util.LocalHazeState
import org.meteora.presentation.util.backAnimation

@Composable
fun App(component: DefaultAppComponent) {
    key(Unit) {
        koinInject<AppLogger>().setup()
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
        val hazeState = rememberHazeState()
        CompositionLocalProvider(LocalHazeState provides hazeState) {
            /*Box(
                modifier = Modifier
                    .fillMaxSize()
                    //.hazeSource(state = hazeState) TODO: add when background ready
                    //.shaderEffect(ShaderOptions.SNOW)
                    .systemBarsPadding()
                    .navigationBarsPadding()
            ) {*/
            AppView(component)
            //}
        }
    }
}

@Composable
fun AppView(component: AppComponent) {
    ChildStack(
        stack = component.stack,
        animation = backAnimation(
            backHandler = component.backHandler,
            onBack = component::onBackClicked,
        ),
    ) {
        when (val child = it.instance) {
            is Child.Locations -> LocationsScreen(child.component)
            is Child.LocationSearch -> LocationSearchScreen(child.component)
            is Child.LocationWeather -> LocationWeatherScreen(child.component)
            is Child.LocationWeatherSheet -> LocationWeatherSheet(child.component)
        }
    }
}
