package org.meteora.presentation.component.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.meteora.data.util.calculateSunProgress
import org.meteora.domain.entity.WeatherInfo
import org.meteora.presentation.component.SunPathView
import org.meteora.presentation.icon.MeteoraIcons
import org.meteora.presentation.icon.SunSet
import org.meteora.presentation.resources.Res
import org.meteora.presentation.resources.sunrise_placeholder
import org.meteora.presentation.resources.sunset
import org.meteora.presentation.theme.MeteoraColor
import org.meteora.presentation.theme.MeteoraTheme
import org.meteora.presentation.util.formatter.hourMinuteFormatter
import org.meteora.presentation.util.preview.WeatherInfoParameters
import kotlin.time.Clock
import kotlin.time.Instant

@Composable
fun SunCard(
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
                imageVector = MeteoraIcons.SunSet,
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

@Preview
@Composable
private fun PreviewSunCard(
    // @PreviewParameter is broken on my AS version (https://youtrack.jetbrains.com/issue/KMT-879)
    // @PreviewParameter(WeatherInfoParameters::class)
    weatherInfo: WeatherInfo = WeatherInfoParameters().values.first()
) {
    MeteoraTheme {
        SunCard(
            sunrise = weatherInfo.sunrise,
            sunset = weatherInfo.sunset,
            modifier = Modifier.width(width = 200.dp)
        )
    }
}
