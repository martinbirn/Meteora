package org.meteora.presentation.screen.locationweather

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import dev.icerock.moko.geo.LocationTracker
import dev.icerock.moko.geo.compose.BindLocationTrackerEffect
import dev.icerock.moko.geo.compose.LocationTrackerAccuracy
import dev.icerock.moko.geo.compose.LocationTrackerFactory
import dev.icerock.moko.geo.compose.rememberLocationTrackerFactory
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.PermissionsControllerFactory
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import org.koin.core.parameter.parametersOf
import org.meteora.domain.entity.LocationInfo
import org.meteora.domain.entity.WeatherInfo
import org.meteora.presentation.screen.locationweather.component.DailyForecastCard
import org.meteora.presentation.screen.locationweather.component.FeelsLikeCard
import org.meteora.presentation.screen.locationweather.component.HourlyForecastCard
import org.meteora.presentation.screen.locationweather.component.HumidityCard
import org.meteora.presentation.screen.locationweather.component.PrecipitationCard
import org.meteora.presentation.screen.locationweather.component.SunCard
import org.meteora.presentation.screen.locationweather.component.UvIndexCard
import org.meteora.presentation.screen.locationweather.component.VisibilityCard
import org.meteora.presentation.screen.locationweather.component.WindCard
import org.meteora.presentation.theme.MeteoraTheme
import org.meteora.presentation.util.description
import org.meteora.presentation.util.preview.WeatherInfoParameters

enum class WeatherSection {
    HOURLIES, DAILIES, FEELS_LIKE, UV_INDEX, WIND, SUN, PRECIPITATION, VISIBILITY, HUMIDITY,
}

@Composable
fun LocationWeatherScreen(
    modifier: Modifier = Modifier,
    locationInfo: LocationInfo? = null,
    header: @Composable () -> Unit = {}
) {
    val viewModel: LocationWeatherViewModel = koinInject {
        parametersOf(locationInfo)
    }
    if (locationInfo == null) {
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

        LaunchedEffect(Unit) {
            viewModel.startTracking(locationTracker)
        }

        DisposableEffect(Unit) {
            onDispose {
                viewModel.stopTracking()
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.refresh()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF8780A3),
                        Color(0xFFaeaabf)
                    )
                )
            )
            .padding(horizontal = MeteoraTheme.dimen.horizontalPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        header()
        val screenState by viewModel.state.collectAsState()
        LocationWeatherScreenContent(
            screenState = screenState
        )
    }
}

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
private fun LocationWeatherScreenContent(
    screenState: LocationWeatherViewModel.State
) {
    val density = LocalDensity.current
    val windowInfo = LocalWindowInfo.current
    val size = with(density) { windowInfo.containerSize.toSize().toDpSize() }
    val windowSize = remember(size) { WindowSizeClass.calculateFromSize(size) }

    when (val weatherState = screenState.weatherState) {
        is LocationWeatherState.Init -> {
            Spacer(modifier = Modifier.height(height = MeteoraTheme.dimen.verticalPadding))
            Text(
                text = "${weatherState.locationInfo.locality}, ${weatherState.locationInfo.country}",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "--",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displayLarge
            )
        }

        is LocationWeatherState.Content -> {
            Spacer(modifier = Modifier.height(height = MeteoraTheme.dimen.verticalPadding))
            Text(
                text = "${weatherState.weatherInfo.location.locality}, ${weatherState.weatherInfo.location.country}",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "${weatherState.weatherInfo.main.temp.toInt()}°",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displayLarge
            )
            Text(
                text = stringResource(weatherState.weatherInfo.weatherCode.description),
                textAlign = TextAlign.Center,
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

            BoxWithConstraints(
                modifier = Modifier.fillMaxWidth()
            ) {
                val maxItemsInEachRow = remember(windowSize.widthSizeClass) {
                    when (windowSize.widthSizeClass) {
                        WindowWidthSizeClass.Compact -> 2
                        else -> 4
                    }
                }
                val spaceBetween = 8.dp
                val singleWidth = (maxWidth - spaceBetween * (maxItemsInEachRow - 1))
                    .div(maxItemsInEachRow)
                val doubleWidth = singleWidth * 2 + spaceBetween
                FlowRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(space = spaceBetween),
                    horizontalArrangement = Arrangement.spacedBy(space = spaceBetween),
                    maxItemsInEachRow = maxItemsInEachRow,
                ) {
                    val hourlies by rememberUpdatedState(weatherState.weatherInfo.hourlies)
                    val dailies by rememberUpdatedState(weatherState.weatherInfo.dailies)

                    Spacer(modifier = Modifier.height(height = 40.dp))
                    HourlyForecastCard(
                        hourlies = hourlies,
                        modifier = Modifier.fillMaxWidth(),
                    )
                    DailyForecastCard(
                        todayTemp = weatherState.weatherInfo.main.temp.toInt(),
                        dailies = dailies,
                        modifier = Modifier.width(width = doubleWidth),
                    )
                    FeelsLikeCard(
                        temp = weatherState.weatherInfo.main.feelsLike.toInt(),
                        modifier = Modifier.width(width = singleWidth),
                    )
                    UvIndexCard(
                        uvIndex = weatherState.weatherInfo.main.uvIndex.toInt(),
                        sunProtectionUntil = "16:00",
                        modifier = Modifier.width(width = singleWidth),
                    )
                    WindCard(
                        windSpeed = weatherState.weatherInfo.windSpeed.toInt(),
                        windDirection = weatherState.weatherInfo.windDirection,
                        windGusts = weatherState.weatherInfo.windGusts.toInt(),
                        modifier = Modifier
                            .width(width = doubleWidth)
                            .height(height = singleWidth),
                    )
                    SunCard(
                        sunrise = weatherState.weatherInfo.sunrise,
                        sunset = weatherState.weatherInfo.sunset,
                        modifier = Modifier.width(width = singleWidth),
                    )
                    PrecipitationCard(
                        precipitation = weatherState.weatherInfo.precipitation.toInt(),
                        modifier = Modifier.width(width = singleWidth),
                    )
                    VisibilityCard(
                        visibility = weatherState.weatherInfo.visibility,
                        modifier = Modifier.width(width = singleWidth),
                    )
                    HumidityCard(
                        humidity = weatherState.weatherInfo.main.humidity,
                        modifier = Modifier.width(width = singleWidth),
                    )
                    Spacer(modifier = Modifier.height(height = MeteoraTheme.dimen.verticalPadding))
                }
            }
        }

        is LocationWeatherState.Error -> {
            Text(
                text = weatherState.throwable.message.orEmpty(),
                modifier = Modifier.padding(top = MeteoraTheme.dimen.verticalPadding),
                style = MaterialTheme.typography.titleLarge
            )
        }

        LocationWeatherState.Loading -> {
            Text(
                text = "Loading",
                modifier = Modifier.padding(top = MeteoraTheme.dimen.verticalPadding),
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Preview
@Composable
private fun PreviewLocationWeatherScreenContent(
    // @PreviewParameter is broken on my AS version (https://youtrack.jetbrains.com/issue/KMT-879)
    // @PreviewParameter(WeatherInfoParameters::class)
    weatherInfo: WeatherInfo = WeatherInfoParameters().values.first()
) {
    MeteoraTheme {
        Column(
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
                .padding(
                    horizontal = MeteoraTheme.dimen.horizontalPadding,
                    vertical = MeteoraTheme.dimen.verticalPadding
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LocationWeatherScreenContent(
                screenState = LocationWeatherViewModel.State(
                    weatherState = LocationWeatherState.Content(
                        weatherInfo = weatherInfo
                    )
                )
            )
        }
    }
}