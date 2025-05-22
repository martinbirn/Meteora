@file:OptIn(ExperimentalSharedTransitionApi::class)

package org.meteora.presentation.screen.locations

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel
import org.meteora.domain.entity.LocationInfo
import org.meteora.domain.entity.WeatherInfo
import org.meteora.domain.entity.WeatherInfoShort
import org.meteora.presentation.component.SearchTextField
import org.meteora.presentation.icon.MeteoraIcons
import org.meteora.presentation.icon.Search
import org.meteora.presentation.resources.Res
import org.meteora.presentation.resources.search_location_placeholder
import org.meteora.presentation.resources.weather
import org.meteora.presentation.theme.MeteoraColor
import org.meteora.presentation.theme.MeteoraTheme
import org.meteora.presentation.util.formatter.hourMinuteFormatter
import org.meteora.presentation.util.preview.PreviewSharedLayout
import org.meteora.presentation.util.preview.WeatherInfoParameters
import org.meteora.presentation.util.status

@Composable
fun LocationsScreen(
    sharedTransitionScope: SharedTransitionScope,
    animatedContentScope: AnimatedContentScope,
    navigateToLocationSearch: () -> Unit,
    navigateToLocationWeather: (LocationInfo) -> Unit
) {
    val viewModel: LocationsViewModel = koinViewModel()

    sharedTransitionScope.LocationsScreenContent(
        animatedContentScope = animatedContentScope,
        screenState = viewModel.state.collectAsState(),
        onSearchClicked = navigateToLocationSearch,
        onWeatherClicked = { key ->
            val locationInfo = viewModel.getLocationInfoByKey(key) ?: return@LocationsScreenContent
            navigateToLocationWeather(locationInfo)
        }
    )
}

@Composable
private fun SharedTransitionScope.LocationsScreenContent(
    animatedContentScope: AnimatedContentScope,
    screenState: State<LocationsViewModel.State>,
    onSearchClicked: () -> Unit,
    onWeatherClicked: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(height = MeteoraTheme.dimen.verticalPadding))
        Text(
            text = stringResource(resource = Res.string.weather),
            modifier = Modifier.padding(horizontal = MeteoraTheme.dimen.horizontalPadding),
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.headlineLarge
        )
        Spacer(modifier = Modifier.height(height = 8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            SearchTextField(
                value = "",
                onValueChange = {},
                modifier = Modifier
                    .weight(weight = 1f)
                    .sharedElement(
                        sharedContentState = rememberSharedContentState(key = "search-text"),
                        animatedVisibilityScope = animatedContentScope
                    )
                    .padding(start = MeteoraTheme.dimen.horizontalPadding),
                readOnly = true,
                onClickWhenReadOnly = onSearchClicked,
                placeholder = stringResource(resource = Res.string.search_location_placeholder),
                leadingIcon = {
                    Icon(
                        imageVector = MeteoraIcons.Search,
                        contentDescription = "Search",
                        tint = MeteoraColor.White50
                    )
                },
            )

            // empty shared element
            Spacer(
                modifier = Modifier
                    .sharedElement(
                        sharedContentState = rememberSharedContentState(key = "cancel-button"),
                        animatedVisibilityScope = animatedContentScope
                    )
                    .padding(end = MeteoraTheme.dimen.horizontalPadding)
                    .width(0.dp)
            )
        }
        Spacer(modifier = Modifier.height(height = 12.dp))
        when (val locationsState = screenState.value.locationsState) {
            LocationsState.Empty -> {

            }

            is LocationsState.Content -> {
                val weathers = remember(locationsState.weathers) { locationsState.weathers }
                LazyColumn(
                    modifier = Modifier.padding(horizontal = MeteoraTheme.dimen.horizontalPadding)
                ) {
                    items(count = weathers.size) { index ->
                        val weather = weathers[index]
                        LocationItemContent(
                            weatherInfo = weather,
                            modifier = Modifier,
                            onClick = onWeatherClicked
                        )
                    }
                }
            }

            is LocationsState.Error -> {
                Text(
                    text = locationsState.throwable.message.orEmpty(),
                    modifier = Modifier
                        .align(alignment = Alignment.CenterHorizontally)
                        .padding(top = MeteoraTheme.dimen.verticalPadding),
                    style = MaterialTheme.typography.titleLarge
                )
            }

            LocationsState.Loading -> {
                // add shimmers
                Text(
                    text = "Loading",
                    modifier = Modifier
                        .align(alignment = Alignment.CenterHorizontally)
                        .padding(top = MeteoraTheme.dimen.verticalPadding),
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }
    }
}

@Composable
private fun LocationItemContent(
    weatherInfo: WeatherInfoShort,
    modifier: Modifier = Modifier,
    onClick: (String) -> Unit
) {
    val lastUpdateFormatted = remember(weatherInfo.lastUpdate) {
        Instant.fromEpochMilliseconds(weatherInfo.lastUpdate)
            .toLocalDateTime(TimeZone.currentSystemDefault()).time
            .format(hourMinuteFormatter)
    }
    val shape = remember { RoundedCornerShape(size = 12.dp) }
    Row(
        modifier = modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth()
            .background(color = MeteoraColor.Black30, shape = shape)
            .clip(shape = shape)
            .clickable { onClick(weatherInfo.key) }
            .padding(all = 12.dp)
            .height(height = 80.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .weight(weight = 1f)
        ) {
            Text(
                text = weatherInfo.name,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = lastUpdateFormatted,
                style = MaterialTheme.typography.bodySmall
            )
            Spacer(modifier = Modifier.weight(weight = 1f))
            Text(
                text = stringResource(resource = weatherInfo.weatherCode.status),
                style = MaterialTheme.typography.bodySmall
            )
        }
        Column(
            modifier = Modifier.fillMaxHeight(),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "${weatherInfo.temp.toInt()}°",
                style = MaterialTheme.typography.displaySmall
            )
            Spacer(modifier = Modifier.weight(weight = 1f))
            Row {
                Text(
                    text = "H:${weatherInfo.tempMax.toInt()}°",
                    style = MaterialTheme.typography.labelSmall
                )
                Spacer(modifier = Modifier.width(width = 4.dp))
                Text(
                    text = "H:${weatherInfo.tempMin.toInt()}°",
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewLocationsScreenContent(
    // @PreviewParameter is broken on my AS version (https://youtrack.jetbrains.com/issue/KMT-879)
    // @PreviewParameter(WeatherInfoParameters::class)
    weathers: Sequence<WeatherInfo> = WeatherInfoParameters().values
) {
    MeteoraTheme {
        val screenState = remember {
            mutableStateOf(
                LocationsViewModel.State(
                    locationsState = LocationsState.Content(
                        weathers = weathers.map {
                            it.toShortInfo(key = it.hashCode().toString())
                        }.toList()
                    )
                )
            )
        }
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
                ),
            contentAlignment = Alignment.TopCenter
        ) {
            PreviewSharedLayout {
                LocationsScreenContent(
                    animatedContentScope = it,
                    screenState = screenState,
                    onSearchClicked = {},
                    onWeatherClicked = {}
                )
            }
        }
    }
}
