package org.meteora.presentation.screen.locationweather.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.meteora.domain.entity.HourlyWeatherInfo
import org.meteora.domain.entity.WeatherInfo
import org.meteora.presentation.icon.Clock
import org.meteora.presentation.icon.MeteoraIcons
import org.meteora.presentation.resources.Res
import org.meteora.presentation.resources.hourly_forecast
import org.meteora.presentation.resources.now
import org.meteora.presentation.theme.MeteoraColor
import org.meteora.presentation.theme.MeteoraTheme
import org.meteora.presentation.util.icon
import org.meteora.presentation.util.preview.WeatherInfoParameters

@Composable
fun HourlyForecastCard(
    hourlies: List<HourlyWeatherInfo>,
    modifier: Modifier = Modifier,
) {
    BlurredContainer(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                imageVector = MeteoraIcons.Clock,
                contentDescription = null,
                colorFilter = ColorFilter.tint(
                    color = MeteoraColor.White30
                )
            )
            Spacer(modifier = Modifier.width(width = 4.dp))
            Text(
                text = stringResource(resource = Res.string.hourly_forecast).uppercase(),
                style = MaterialTheme.typography.labelLarge.copy(
                    color = MeteoraColor.White30
                )
            )
        }
        Spacer(modifier = Modifier.height(height = 8.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(height = 4.dp))
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(space = 16.dp),
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
                        imageVector = hourly.weatherCode.icon,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .size(24.dp)
                    )
                    Text(
                        text = "${hourly.temp.toInt()}°",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewHourlyForecastCard(
    // @PreviewParameter is broken on my AS version (https://youtrack.jetbrains.com/issue/KMT-879)
    // @PreviewParameter(WeatherInfoParameters::class)
    weatherInfo: WeatherInfo = WeatherInfoParameters().values.first()
) {
    MeteoraTheme {
        HourlyForecastCard(
            hourlies = weatherInfo.hourlies
        )
    }
}
