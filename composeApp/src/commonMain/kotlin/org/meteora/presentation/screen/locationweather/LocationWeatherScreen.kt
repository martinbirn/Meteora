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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize
import dev.icerock.moko.geo.compose.BindLocationTrackerEffect
import dev.icerock.moko.geo.compose.LocationTrackerAccuracy
import dev.icerock.moko.geo.compose.rememberLocationTrackerFactory
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.meteora.presentation.component.weather.DailyForecastCard
import org.meteora.presentation.component.weather.FeelsLikeCard
import org.meteora.presentation.component.weather.HourlyForecastCard
import org.meteora.presentation.component.weather.HumidityCard
import org.meteora.presentation.component.weather.PrecipitationCard
import org.meteora.presentation.component.weather.SunCard
import org.meteora.presentation.component.weather.UvIndexCard
import org.meteora.presentation.component.weather.VisibilityCard
import org.meteora.presentation.component.weather.WindCard
import org.meteora.presentation.screen.locationweather.component.LocationWeatherComponent
import org.meteora.presentation.screen.locationweather.component.PreviewLocationWeatherComponent
import org.meteora.presentation.theme.MeteoraColor
import org.meteora.presentation.theme.MeteoraTheme
import org.meteora.presentation.util.description

enum class WeatherSection {
    HOURLIES, DAILIES, FEELS_LIKE, UV_INDEX, WIND, SUN, PRECIPITATION, VISIBILITY, HUMIDITY,
}

@Composable
fun LocationWeatherScreen(
    component: LocationWeatherComponent,
    modifier: Modifier = Modifier,
    header: @Composable () -> Unit = {}
) {
    if (component.initialLocation == null) {
        val permissionFactory = rememberPermissionsControllerFactory()
        val permissionsController = remember(permissionFactory) {
            permissionFactory.createPermissionsController()
        }
        val locationTrackerFactory = rememberLocationTrackerFactory(LocationTrackerAccuracy.Best)
        val locationTracker = remember(locationTrackerFactory) {
            locationTrackerFactory.createLocationTracker(permissionsController)
        }

        BindEffect(permissionsController)
        BindLocationTrackerEffect(locationTracker)

        LaunchedEffect(Unit) {
            component.startTracking(locationTracker)
        }

        DisposableEffect(Unit) {
            onDispose {
                component.stopTracking()
            }
        }
    }

    LaunchedEffect(Unit) {
        component.refresh()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(
                        MeteoraColor.DarkSkyBlue,
                        MeteoraColor.Purple
                    )
                )
            )
            .statusBarsPadding()
            .padding(
                start = MeteoraTheme.dimen.horizontalPadding,
                end = MeteoraTheme.dimen.horizontalPadding
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        header()
        LocationWeatherScreenContent(
            component = component
        )
    }
}

@Composable
private fun LocationWeatherScreenContent(
    component: LocationWeatherComponent
) {
    val screenState by component.weatherState.collectAsState()
    val density = LocalDensity.current
    val windowInfo = LocalWindowInfo.current
    val size = with(density) { windowInfo.containerSize.toSize().toDpSize() }
    val windowSize = remember(size) { WindowSizeClass.calculateFromSize(size) }

    when (val weatherState = screenState) {
        is LocationWeatherUiState.Init -> {
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

        is LocationWeatherUiState.Loading -> {
            Text(
                text = "Loading",
                modifier = Modifier.padding(top = MeteoraTheme.dimen.verticalPadding),
                style = MaterialTheme.typography.titleLarge
            )
        }

        is LocationWeatherUiState.Error -> {
            Text(
                text = weatherState.throwable.message.orEmpty(),
                modifier = Modifier.padding(top = MeteoraTheme.dimen.verticalPadding),
                style = MaterialTheme.typography.titleLarge
            )
        }

        is LocationWeatherUiState.Content -> {
            Spacer(modifier = Modifier.height(height = MeteoraTheme.dimen.verticalPadding))
            Text(
                text = "${weatherState.weatherInfo.locationInfo.locality}, ${weatherState.weatherInfo.locationInfo.country}",
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
                    Spacer(modifier = Modifier.navigationBarsPadding())
                }
            }
        }
    }
}

@Preview
@Composable
private fun LocationWeatherScreenPreview() {
    MeteoraTheme {
        LocationWeatherScreen(PreviewLocationWeatherComponent())
    }
}
