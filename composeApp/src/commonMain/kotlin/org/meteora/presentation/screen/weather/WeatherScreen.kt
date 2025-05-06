package org.meteora.presentation.screen.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.ExperimentalHazeMaterialsApi
import dev.chrisbanes.haze.materials.HazeMaterials
import dev.chrisbanes.haze.rememberHazeState
import dev.icerock.moko.geo.LocationTracker
import dev.icerock.moko.geo.compose.BindLocationTrackerEffect
import dev.icerock.moko.geo.compose.LocationTrackerAccuracy
import dev.icerock.moko.geo.compose.LocationTrackerFactory
import dev.icerock.moko.geo.compose.rememberLocationTrackerFactory
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.PermissionsControllerFactory
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import org.meteora.domain.entity.HourlyWeatherInfo
import org.meteora.domain.entity.WeatherInfo
import org.meteora.presentation.resources.Res
import org.meteora.presentation.resources.now
import org.meteora.presentation.theme.MeteoraColor
import org.meteora.presentation.theme.MeteoraTheme
import org.meteora.presentation.util.LocalHazeState
import org.meteora.presentation.util.description
import org.meteora.presentation.util.icon
import org.meteora.presentation.util.preview.WeatherInfoParameters

@Composable
fun WeatherScreen() {
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
        parametersOf(locationTracker)
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
        val screenState by viewModel.state.collectAsState()
        WeatherScreenContent(
            screenState = screenState
        )
    }
}

@Composable
private fun WeatherScreenContent(
    screenState: WeatherViewModel.State
) {
    val hazeState = rememberHazeState()
    CompositionLocalProvider(LocalHazeState provides hazeState) {
        when (val weatherState = screenState.weatherState) {
            is WeatherState.Content -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .hazeSource(hazeState),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "${weatherState.weatherInfo.location.city}, ${weatherState.weatherInfo.location.country}",
                        style = MaterialTheme.typography.titleLarge
                    )
                    Text(
                        text = "${weatherState.weatherInfo.main.temp.toInt()}°",
                        style = MaterialTheme.typography.displayLarge
                    )
                    Text(
                        text = stringResource(weatherState.weatherInfo.weatherCode.description),
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

                    Spacer(modifier = Modifier.height(height = 40.dp))

                    val hourlies by rememberUpdatedState(weatherState.weatherInfo.hourlies)
                    HourliesContent(hourlies)
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
}

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
private fun HourliesContent(hourlies: List<HourlyWeatherInfo>) {
    val lazyListState = rememberLazyListState()
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MeteoraColor.Black30, shape = RoundedCornerShape(size = 12.dp))
            .padding(all = 12.dp)
            .hazeEffect(
                state = LocalHazeState.current,
                style = HazeMaterials.ultraThin()
            ),
        state = lazyListState,
    ) {
        items(
            count = hourlies.size,
            key = { index -> hourlies[index].index }
        ) { index ->
            val hourly = hourlies[index]
            val hour = if (index == 0) {
                stringResource(Res.string.now)
            } else {
                hourly.hour.toString()
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = hour,
                    style = MaterialTheme.typography.labelMedium
                )
                Image(
                    painter = painterResource(resource = hourly.weatherCode.icon),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
                Text(
                    text = "${hourly.temp.toInt()}°",
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewWeatherScreenContent(
    // @PreviewParameter is broken on my AS version (https://youtrack.jetbrains.com/issue/KMT-879)
    // @PreviewParameter(WeatherInfoParameters::class)
    weatherInfo: WeatherInfo = WeatherInfoParameters().values.first()
) {
    MeteoraTheme {
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
                screenState = WeatherViewModel.State(
                    weatherState = WeatherState.Content(
                        weatherInfo = weatherInfo
                    )
                )
            )
        }
    }
}