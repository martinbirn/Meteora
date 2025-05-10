package org.meteora.presentation.screen.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
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
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.parameter.parametersOf
import org.meteora.data.util.calculateSunProgress
import org.meteora.domain.entity.DailyWeatherInfo
import org.meteora.domain.entity.DirectionAngle
import org.meteora.domain.entity.HourlyWeatherInfo
import org.meteora.domain.entity.WeatherInfo
import org.meteora.presentation.icon.CalendarIcon
import org.meteora.presentation.icon.DropletIcon
import org.meteora.presentation.icon.EyeIcon
import org.meteora.presentation.icon.SunIcon
import org.meteora.presentation.icon.SunSetIcon
import org.meteora.presentation.icon.ThermometerIcon
import org.meteora.presentation.icon.WindIcon
import org.meteora.presentation.resources.Res
import org.meteora.presentation.resources.direction
import org.meteora.presentation.resources.extreme
import org.meteora.presentation.resources.feels_like
import org.meteora.presentation.resources.gusts
import org.meteora.presentation.resources.high
import org.meteora.presentation.resources.humidity
import org.meteora.presentation.resources.low
import org.meteora.presentation.resources.moderate
import org.meteora.presentation.resources.now
import org.meteora.presentation.resources.precipitation
import org.meteora.presentation.resources.sunrise_placeholder
import org.meteora.presentation.resources.sunset
import org.meteora.presentation.resources.ten_day_forecast
import org.meteora.presentation.resources.today
import org.meteora.presentation.resources.use_sun_protection_until
import org.meteora.presentation.resources.uv_index
import org.meteora.presentation.resources.very_high
import org.meteora.presentation.resources.visibility
import org.meteora.presentation.resources.wind
import org.meteora.presentation.screen.weather.component.SunPathView
import org.meteora.presentation.screen.weather.component.WindCompass
import org.meteora.presentation.theme.MeteoraColor
import org.meteora.presentation.theme.MeteoraTheme
import org.meteora.presentation.util.LocalHazeState
import org.meteora.presentation.util.description
import org.meteora.presentation.util.directionName
import org.meteora.presentation.util.formatter.hourMinuteFormatter
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
            .padding(horizontal = MeteoraTheme.dimen.horizontalPadding),
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
                        .verticalScroll(state = rememberScrollState())
                        .hazeSource(hazeState),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(height = MeteoraTheme.dimen.verticalPadding))
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

                    Spacer(modifier = Modifier.height(height = 8.dp))

                    val dailies by rememberUpdatedState(weatherState.weatherInfo.dailies)
                    DailiesContent(
                        todayTemp = weatherState.weatherInfo.main.temp.toInt(),
                        dailies = dailies
                    )

                    Spacer(modifier = Modifier.height(height = 8.dp))

                    Row {
                        FeelsLikeContent(
                            temp = weatherState.weatherInfo.main.feelsLike.toInt(),
                            modifier = Modifier.weight(weight = 1f)
                        )
                        Spacer(modifier = Modifier.width(width = 8.dp))
                        UvIndexCard(
                            uvIndex = weatherState.weatherInfo.main.uvIndex.toInt(),
                            sunProtectionUntil = "16:00",
                            modifier = Modifier.weight(weight = 1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(height = 8.dp))

                    WindContent(
                        windSpeed = weatherState.weatherInfo.windSpeed.toInt(),
                        windDirection = weatherState.weatherInfo.windDirection,
                        windGusts = weatherState.weatherInfo.windGusts.toInt(),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(height = 8.dp))

                    Row {
                        SunContent(
                            sunrise = weatherState.weatherInfo.sunrise,
                            sunset = weatherState.weatherInfo.sunset,
                            modifier = Modifier.weight(weight = 1f)
                        )
                        Spacer(modifier = Modifier.width(width = 8.dp))
                        PrecipitationContent(
                            precipitation = weatherState.weatherInfo.precipitation.toInt(),
                            modifier = Modifier.weight(weight = 1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(height = 8.dp))

                    Row {
                        VisibilityContent(
                            visibility = weatherState.weatherInfo.visibility,
                            modifier = Modifier.weight(weight = 1f)
                        )
                        Spacer(modifier = Modifier.width(width = 8.dp))
                        HumidityContent(
                            humidity = weatherState.weatherInfo.main.humidity,
                            modifier = Modifier.weight(weight = 1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(height = MeteoraTheme.dimen.verticalPadding))
                }
            }

            is WeatherState.Error -> {
                Text(
                    text = weatherState.throwable.message.orEmpty(),
                    modifier = Modifier.padding(top = MeteoraTheme.dimen.verticalPadding),
                    style = MaterialTheme.typography.titleLarge
                )
            }

            WeatherState.Loading -> {
                Text(
                    text = "Loading",
                    modifier = Modifier.padding(top = MeteoraTheme.dimen.verticalPadding),
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
        contentPadding = PaddingValues(start = 2.dp, end = 2.dp)
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
                    style = MaterialTheme.typography.labelLarge
                )
                Image(
                    painter = painterResource(resource = hourly.weatherCode.icon),
                    contentDescription = null,
                    modifier = Modifier.size(40.dp)
                )
                Text(
                    text = "${hourly.temp.toInt()}°",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
private fun DailiesContent(
    todayTemp: Int,
    dailies: List<DailyWeatherInfo>,
    modifier: Modifier = Modifier,
) {
    val globalMin = remember(dailies) { dailies.minOf { it.tempMin }.toInt() }
    val globalMax = remember(dailies) { dailies.maxOf { it.tempMax }.toInt() }
    Column(
        modifier = modifier.fillMaxWidth()
            .background(color = MeteoraColor.Black30, shape = RoundedCornerShape(size = 12.dp))
            .padding(all = 12.dp)
            .hazeEffect(
                state = LocalHazeState.current,
                style = HazeMaterials.ultraThin()
            )
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                imageVector = CalendarIcon,
                contentDescription = null,
                colorFilter = ColorFilter.tint(
                    color = MeteoraColor.White30
                )
            )
            Spacer(modifier = Modifier.width(width = 4.dp))
            Text(
                text = stringResource(resource = Res.string.ten_day_forecast),
                style = MaterialTheme.typography.labelMedium.copy(
                    color = MeteoraColor.White30
                )
            )
        }
        Spacer(modifier = Modifier.height(height = 8.dp))
        dailies.forEachIndexed { index, daily ->
            HorizontalDivider()
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val dayOfWeek = if (index == 0) {
                    stringResource(Res.string.today)
                } else {
                    daily.dayOfWeek
                }
                Text(
                    text = dayOfWeek,
                    modifier = Modifier.weight(weight = 1f),
                    style = MaterialTheme.typography.bodyLarge
                )
                Image(
                    painter = painterResource(resource = daily.weatherCode.icon),
                    contentDescription = null,
                    modifier = Modifier.weight(weight = 1f).size(40.dp)
                )
                Row(
                    modifier = Modifier.weight(weight = 2f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${daily.tempMin.toInt()}°",
                        style = MaterialTheme.typography.bodyLarge.copy(
                            color = MeteoraColor.White30
                        )
                    )
                    Spacer(modifier = Modifier.width(width = 8.dp))
                    DailyTemperatureBar(
                        day = daily,
                        globalMin = globalMin,
                        globalMax = globalMax,
                        currentTemp = todayTemp.takeIf { index == 0 },
                        modifier = Modifier.weight(weight = 1f)
                    )
                    Spacer(modifier = Modifier.width(width = 8.dp))
                    Text(
                        text = "${daily.tempMax.toInt()}°",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Composable
private fun DailyTemperatureBar(
    day: DailyWeatherInfo,
    globalMin: Int,
    globalMax: Int,
    currentTemp: Int?,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(4.dp)
            .clip(RoundedCornerShape(3.dp))
            .background(color = MeteoraColor.TemperatureTrackBackground)
            .drawBehind {
                val startFraction =
                    ((day.tempMin.toInt() - globalMin) / (globalMax - globalMin).toFloat()).coerceIn(
                        0.0f,
                        1.0f
                    )
                val endFraction =
                    ((day.tempMax.toInt() - globalMin) / (globalMax - globalMin).toFloat()).coerceIn(
                        0.0f,
                        1.0f
                    )

                val startX = size.width * startFraction
                val endX = size.width * endFraction

                val gradient = Brush.horizontalGradient(
                    colors = listOf(
                        MeteoraColor.TemperatureTrackGradientStart,
                        MeteoraColor.TemperatureTrackGradientEnd
                    ),
                    startX = startX,
                    endX = endX
                )

                drawRoundRect(
                    brush = gradient,
                    topLeft = Offset(startX, 0f),
                    size = Size(endX - startX, size.height),
                    cornerRadius = CornerRadius(3.dp.toPx(), 3.dp.toPx())
                )

                if (currentTemp != null) {
                    val currentFraction =
                        ((currentTemp - globalMin) / (globalMax - globalMin).toFloat())
                            .coerceIn(0.0f, 1.0f)

                    val circleX = size.width * currentFraction
                    val circleY = size.height / 2

                    drawCircle(
                        color = MeteoraColor.Black30,
                        radius = 4.dp.toPx(),
                        center = Offset(circleX, circleY)
                    )
                    drawCircle(
                        color = MeteoraColor.White,
                        center = Offset(circleX, circleY)
                    )
                }
            }
    )
}

@Composable
private fun FeelsLikeContent(
    temp: Int,
    modifier: Modifier = Modifier
) {
    SquareContainer(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                imageVector = ThermometerIcon,
                contentDescription = null,
                colorFilter = ColorFilter.tint(
                    color = MeteoraColor.White30
                )
            )
            Spacer(modifier = Modifier.width(width = 4.dp))
            Text(
                text = stringResource(resource = Res.string.feels_like),
                style = MaterialTheme.typography.labelMedium.copy(
                    color = MeteoraColor.White30
                )
            )
        }
        Spacer(modifier = Modifier.height(height = 12.dp))
        Text(
            text = "${temp}°",
            style = MaterialTheme.typography.displaySmall
        )
        Spacer(modifier = Modifier.weight(weight = 1f))
        Text(
            text = "Wind is making it feel cooler",
            style = MaterialTheme.typography.labelLarge.copy(
                color = MeteoraColor.White
            )
        )
    }
}

@Composable
private fun UvIndexCard(
    uvIndex: Int,
    sunProtectionUntil: String,
    modifier: Modifier = Modifier,
) {
    SquareContainer(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                imageVector = SunIcon,
                contentDescription = null,
                colorFilter = ColorFilter.tint(
                    color = MeteoraColor.White30
                )
            )
            Spacer(modifier = Modifier.width(width = 4.dp))
            Text(
                text = stringResource(resource = Res.string.uv_index),
                style = MaterialTheme.typography.labelMedium.copy(
                    color = MeteoraColor.White30
                )
            )
        }
        Spacer(modifier = Modifier.height(height = 12.dp))
        Text(
            text = uvIndex.toString(),
            style = MaterialTheme.typography.displaySmall
        )
        Text(
            text = when (uvIndex) {
                in 0..2 -> stringResource(Res.string.low)
                in 3..5 -> stringResource(Res.string.moderate)
                in 6..7 -> stringResource(Res.string.high)
                in 8..10 -> stringResource(Res.string.very_high)
                else -> stringResource(Res.string.extreme)
            },
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.weight(weight = 1f))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .clip(RoundedCornerShape(3.dp))
                .background(
                    Brush.horizontalGradient(
                        listOf(
                            MeteoraColor.UvLow,
                            MeteoraColor.UvModerate,
                            MeteoraColor.UvHigh,
                            MeteoraColor.UvVeryHigh,
                            MeteoraColor.UvExtreme
                        )
                    )
                )
                .drawBehind {
                    val circleX = size.width * uvIndex / 11f
                    val circleY = size.height / 2
                    drawCircle(
                        color = MeteoraColor.Black30,
                        radius = 4.dp.toPx(),
                        center = Offset(circleX, circleY)
                    )
                    drawCircle(
                        color = Color.White,
                        center = Offset(circleX, circleY)
                    )
                }
        )
        Spacer(modifier = Modifier.height(height = 4.dp))
        Text(
            text = stringResource(Res.string.use_sun_protection_until, sunProtectionUntil),
            style = MaterialTheme.typography.labelLarge.copy(
                color = MeteoraColor.White
            )
        )
    }
}

@Composable
private fun WindContent(
    windSpeed: Int,
    windDirection: DirectionAngle,
    windGusts: Int,
    modifier: Modifier = Modifier
) {
    BluredContainer(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                imageVector = WindIcon,
                contentDescription = null,
                colorFilter = ColorFilter.tint(
                    color = MeteoraColor.White30
                )
            )
            Spacer(modifier = Modifier.width(width = 4.dp))
            Text(
                text = stringResource(resource = Res.string.wind).uppercase(),
                style = MaterialTheme.typography.labelLarge.copy(
                    color = MeteoraColor.White30
                )
            )
        }
        Row(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.fillMaxWidth(fraction = 0.6f)
            ) {
                Spacer(modifier = Modifier.weight(weight = 1f))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp, bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(resource = Res.string.wind).replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase() else it.toString()
                        },
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text(
                        text = "$windSpeed m/s",
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = MeteoraColor.White30
                        )
                    )
                }

                HorizontalDivider()

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(resource = Res.string.gusts),
                        style = MaterialTheme.typography.labelLarge
                    )
                    Text(
                        text = "$windGusts m/s",
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = MeteoraColor.White30
                        )
                    )
                }

                HorizontalDivider()

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(resource = Res.string.direction),
                        style = MaterialTheme.typography.labelLarge
                    )
                    val directionName = directionName(windDirection)
                    Text(
                        text = "$windDirection° $directionName",
                        style = MaterialTheme.typography.labelLarge.copy(
                            color = MeteoraColor.White30
                        )
                    )
                }
            }
            Spacer(modifier = Modifier.width(width = 12.dp))
            WindCompass(
                windSpeed = windSpeed,
                windDirection = windDirection,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun SunContent(
    sunrise: Long,
    sunset: Long,
    modifier: Modifier = Modifier
) {
    val sunsetLocalDateTime = remember(sunset) {
        Instant.fromEpochSeconds(sunset).toLocalDateTime(TimeZone.currentSystemDefault())
    }
    val sunriseLocalDateTime = remember(sunrise) {
        Instant.fromEpochSeconds(sunrise).toLocalDateTime(TimeZone.currentSystemDefault())
    }
    SquareContainer(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                imageVector = SunSetIcon,
                contentDescription = null,
                colorFilter = ColorFilter.tint(
                    color = MeteoraColor.White30
                )
            )
            Spacer(modifier = Modifier.width(width = 4.dp))
            Text(
                text = stringResource(resource = Res.string.sunset),
                style = MaterialTheme.typography.labelMedium.copy(
                    color = MeteoraColor.White30
                )
            )
        }
        Text(
            text = sunsetLocalDateTime.time.format(hourMinuteFormatter),
            style = MaterialTheme.typography.displaySmall
        )
        SunPathView(
            sunProgress = calculateSunProgress(
                currentTimeSeconds = Clock.System.now().epochSeconds,
                sunriseSeconds = sunrise,
                sunsetSeconds = sunset
            ),
            modifier = Modifier.weight(weight = 1f)
        )
        Spacer(modifier = Modifier.weight(weight = 1f))
        Text(
            text = stringResource(
                Res.string.sunrise_placeholder,
                sunriseLocalDateTime.time.format(hourMinuteFormatter)
            ),
            style = MaterialTheme.typography.labelLarge.copy(
                color = MeteoraColor.White
            )
        )
    }
}

@Composable
private fun PrecipitationContent(
    precipitation: Int,
    modifier: Modifier = Modifier
) {
    SquareContainer(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                imageVector = DropletIcon,
                contentDescription = null,
                colorFilter = ColorFilter.tint(
                    color = MeteoraColor.White30
                )
            )
            Spacer(modifier = Modifier.width(width = 4.dp))
            Text(
                text = stringResource(resource = Res.string.precipitation),
                style = MaterialTheme.typography.labelMedium.copy(
                    color = MeteoraColor.White30
                )
            )
        }
        Spacer(modifier = Modifier.height(height = 12.dp))
        Text(
            text = "$precipitation mm",
            style = MaterialTheme.typography.displaySmall
        )
        Text(
            text = stringResource(Res.string.today),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.weight(weight = 1f))
        Text(
            text = "Next expected is 2 mm on Sat",
            style = MaterialTheme.typography.labelLarge.copy(
                color = MeteoraColor.White
            )
        )
    }
}

@Composable
private fun VisibilityContent(
    visibility: Double,
    modifier: Modifier = Modifier
) {
    SquareContainer(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                imageVector = EyeIcon,
                contentDescription = null,
                colorFilter = ColorFilter.tint(
                    color = MeteoraColor.White30
                )
            )
            Spacer(modifier = Modifier.width(width = 4.dp))
            Text(
                text = stringResource(resource = Res.string.visibility),
                style = MaterialTheme.typography.labelMedium.copy(
                    color = MeteoraColor.White30
                )
            )
        }
        Spacer(modifier = Modifier.height(height = 12.dp))
        Text(
            text = if (visibility > 1000) "${(visibility / 1000).toInt()} km" else "${visibility.toInt()} m",
            style = MaterialTheme.typography.displaySmall
        )
        Spacer(modifier = Modifier.weight(weight = 1f))
        Text(
            text = "Perfectly clear view.",
            style = MaterialTheme.typography.labelLarge.copy(
                color = MeteoraColor.White
            )
        )
    }
}

@Composable
private fun HumidityContent(
    humidity: Int,
    modifier: Modifier = Modifier
) {
    SquareContainer(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                imageVector = DropletIcon,
                contentDescription = null,
                colorFilter = ColorFilter.tint(
                    color = MeteoraColor.White30
                )
            )
            Spacer(modifier = Modifier.width(width = 4.dp))
            Text(
                text = stringResource(resource = Res.string.humidity),
                style = MaterialTheme.typography.labelMedium.copy(
                    color = MeteoraColor.White30
                )
            )
        }
        Spacer(modifier = Modifier.height(height = 12.dp))
        Text(
            text = "$humidity%",
            style = MaterialTheme.typography.displaySmall
        )
        Spacer(modifier = Modifier.weight(weight = 1f))
        Text(
            text = "The dew point is 1° right now",
            style = MaterialTheme.typography.labelLarge.copy(
                color = MeteoraColor.White
            )
        )
    }
}

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
private fun SquareContainer(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    BluredContainer(
        modifier = modifier.aspectRatio(ratio = 1f),
        content = content
    )
}

@OptIn(ExperimentalHazeMaterialsApi::class)
@Composable
private fun BluredContainer(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .background(color = MeteoraColor.Black30, shape = RoundedCornerShape(size = 12.dp))
            .padding(all = 12.dp)
            .hazeEffect(
                state = LocalHazeState.current,
                style = HazeMaterials.ultraThin()
            ),
        content = content
    )
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