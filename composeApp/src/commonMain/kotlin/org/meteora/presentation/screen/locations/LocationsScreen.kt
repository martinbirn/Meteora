package org.meteora.presentation.screen.locations

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.hazeSource
import dev.chrisbanes.haze.materials.HazeMaterials
import dev.chrisbanes.haze.rememberHazeState
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.meteora.domain.entity.WeatherInfo
import org.meteora.domain.entity.WeatherInfoShort
import org.meteora.presentation.component.CollapsibleTopAppBar
import org.meteora.presentation.component.SearchTextField
import org.meteora.presentation.decompose.LocationsComponent
import org.meteora.presentation.decompose.LocationsUiState
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
import kotlin.time.Instant

@Composable
fun LocationsScreen(
    component: LocationsComponent,
    modifier: Modifier = Modifier,
) {
    LocationsScreenContent(
        screenState = component.uiState.collectAsState(),
        modifier = modifier,
        onSearchClicked = component::navigateToLocationSearch,
        onWeatherClicked = { key ->
            val locationInfo = component.getLocationInfoByKey(key) ?: return@LocationsScreenContent
            component.navigateToLocationWeather(locationInfo)
        }
    )
}

@Composable
private fun LocationsScreenContent(
    screenState: State<LocationsUiState>,
    modifier: Modifier = Modifier,
    onSearchClicked: () -> Unit,
    onWeatherClicked: (String) -> Unit
) {
    val hazeState = rememberHazeState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()

    Scaffold(
        modifier = modifier,
        topBar = {
            CollapsibleTopAppBar(
                title = stringResource(resource = Res.string.weather),
                searchField = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SearchTextField(
                            value = "",
                            onValueChange = {},
                            modifier = Modifier
                                .weight(weight = 1f)
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
                                .padding(end = MeteoraTheme.dimen.horizontalPadding)
                                .width(0.dp)
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
                modifier = Modifier
                    .hazeEffect(
                        state = hazeState,
                        style = HazeMaterials.regular(containerColor = MeteoraColor.Black30)
                    ),
                expandedHeight = 48.dp
            )
        }
    ) { innerPadding ->
        when (val locationsState = screenState.value) {
            is LocationsUiState.Empty -> {
                Spacer(modifier = Modifier.fillMaxSize())
            }

            is LocationsUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Loading",
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }

            is LocationsUiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = locationsState.throwable.message.orEmpty(),
                        style = MaterialTheme.typography.titleLarge
                    )
                }
            }

            is LocationsUiState.Content -> {
                val weathers = remember(locationsState.weathers) { locationsState.weathers }
                LazyColumn(
                    modifier = Modifier
                        .hazeSource(hazeState)
                        .fillMaxSize()
                        .nestedScroll(scrollBehavior.nestedScrollConnection),
                    contentPadding = PaddingValues(
                        start = MeteoraTheme.dimen.horizontalPadding,
                        // cause recompositions, but supports transparent toolbar behavior on scroll
                        top = innerPadding.calculateTopPadding(),
                        end = MeteoraTheme.dimen.horizontalPadding,
                        bottom = MeteoraTheme.dimen.verticalPadding
                    )
                ) {
                    items(
                        count = weathers.size,
                        key = { weathers[it].key }
                    ) { index ->
                        val weather = weathers[index]
                        LocationItemContent(
                            weatherInfo = weather,
                            modifier = Modifier,
                            onClick = onWeatherClicked
                        )
                    }
                }
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
            .background(color = MeteoraColor.DarkSkyBlue, shape = shape)
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
                LocationsUiState.Content(
                    weathers = weathers.map {
                        it.toShortInfo(key = it.hashCode().toString())
                    }.toList()
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
                    screenState = screenState,
                    onSearchClicked = {},
                    onWeatherClicked = {}
                )
            }
        }
    }
}
