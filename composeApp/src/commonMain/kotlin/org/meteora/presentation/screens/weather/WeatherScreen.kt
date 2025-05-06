package org.meteora.presentation.screens.weather

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import coil3.compose.AsyncImage
import dev.icerock.moko.geo.LocationTracker
import dev.icerock.moko.geo.compose.BindLocationTrackerEffect
import dev.icerock.moko.geo.compose.LocationTrackerAccuracy
import dev.icerock.moko.geo.compose.LocationTrackerFactory
import dev.icerock.moko.geo.compose.rememberLocationTrackerFactory
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.PermissionsControllerFactory
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import org.meteora.di.allModules
import org.meteora.presentation.theme.MeteoraTheme

@Composable
fun WeatherScreen() {
    MaterialTheme {
        val permissionFactory: PermissionsControllerFactory = rememberPermissionsControllerFactory()
        val permissionsController: PermissionsController = remember(permissionFactory) {
            permissionFactory.createPermissionsController()
        }
        val locationTrackerFactory: LocationTrackerFactory = rememberLocationTrackerFactory(
            LocationTrackerAccuracy.Best
        )
        val locationTracker: LocationTracker = remember(locationTrackerFactory) {
            locationTrackerFactory.createLocationTracker(permissionsController)
        }

        BindEffect(permissionsController)
        BindLocationTrackerEffect(locationTracker)

        val viewModel: WeatherViewModel = koinViewModel {
            parametersOf(locationTracker, permissionsController)
        }

        LaunchedEffect(Unit) {
            viewModel.refresh()
            viewModel.startTracking()
        }

        DisposableEffect(Unit) {
            onDispose {
                viewModel.stopTracking()
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    horizontal = MeteoraTheme.dimen.horizontalPadding,
                    vertical = MeteoraTheme.dimen.verticalPadding
                ),
            contentAlignment = Alignment.TopCenter
        ) {
            WeatherScreenContent(
                viewModel = viewModel
            )
        }
    }
}

@Composable
private fun WeatherScreenContent(
    viewModel: WeatherViewModel
) {
    val state by viewModel.state.collectAsState()

    when (val weatherState = state.weatherState) {
        is WeatherState.Content -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "${weatherState.weatherInfo.location.city}, ${weatherState.weatherInfo.location.country}",
                    style = MaterialTheme.typography.titleLarge
                )
                Row(
                    //modifier = Modifier.height(intrinsicSize = IntrinsicSize.Max)
                ) {
                    Text(
                        text = "${weatherState.weatherInfo.main.temp.toInt()}°",
                        style = MaterialTheme.typography.displayLarge
                    )
                    /*AsyncImage(
                        model = weatherState.weatherInfo.weather?.icon,
                        contentDescription = null,
                        modifier = Modifier.fillMaxHeight()
                    )*/
                }
                Text(
                    text = "${weatherState.weatherInfo.weather?.main}",
                    style = MaterialTheme.typography.bodyMedium
                )
                Row {
                    Text(
                        text = "H:${weatherState.weatherInfo.main.tempMax.toInt()}°",
                        style = MaterialTheme.typography.labelMedium
                    )
                    Text(
                        text = "L:${weatherState.weatherInfo.main.tempMin.toInt()}°",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }

        is WeatherState.Error -> {
            Text(
                text = weatherState.throwable.message.orEmpty(),
                style = MaterialTheme.typography.titleLarge
            )
        }

        WeatherState.Loading -> {
            Text(
                text = "Loading",
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Preview
@Composable
private fun PreviewWeatherScreenContent() {
    MeteoraTheme {
        KoinApplication(application = {
            modules(allModules)
        }) {
            val permissionFactory: PermissionsControllerFactory =
                rememberPermissionsControllerFactory()
            val permissionsController: PermissionsController = remember(permissionFactory) {
                permissionFactory.createPermissionsController()
            }
            val locationTrackerFactory: LocationTrackerFactory = rememberLocationTrackerFactory(
                LocationTrackerAccuracy.Best
            )
            val locationTracker: LocationTracker = remember(locationTrackerFactory) {
                locationTrackerFactory.createLocationTracker(permissionsController)
            }
            WeatherScreenContent(
                viewModel = koinViewModel {
                    parametersOf(locationTracker, permissionsController)
                }
            )
        }
    }
}