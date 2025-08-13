package org.meteora.presentation.component.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import org.meteora.domain.entity.DirectionAngle
import org.meteora.domain.entity.WeatherInfo
import org.meteora.presentation.component.WindCompass
import org.meteora.presentation.icon.MeteoraIcons
import org.meteora.presentation.icon.Wind
import org.meteora.presentation.resources.Res
import org.meteora.presentation.resources.direction
import org.meteora.presentation.resources.gusts
import org.meteora.presentation.resources.wind
import org.meteora.presentation.theme.MeteoraColor
import org.meteora.presentation.theme.MeteoraTheme
import org.meteora.presentation.util.directionName
import org.meteora.presentation.util.preview.WeatherInfoParameters

@Composable
fun WindCard(
    windSpeed: Int,
    windDirection: DirectionAngle,
    windGusts: Int,
    modifier: Modifier = Modifier
) {
    BlurredContainer(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                imageVector = MeteoraIcons.Wind,
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
                Spacer(modifier = Modifier.weight(weight = 1f))
            }
            Spacer(modifier = Modifier.width(width = 12.dp))
            WindCompass(
                windSpeed = windSpeed,
                windDirection = windDirection,
                modifier = Modifier.fillMaxHeight()
            )
        }
    }
}

@Preview
@Composable
private fun PreviewWindCard(
    // @PreviewParameter is broken on my AS version (https://youtrack.jetbrains.com/issue/KMT-879)
    // @PreviewParameter(WeatherInfoParameters::class)
    weatherInfo: WeatherInfo = WeatherInfoParameters().values.first()
) {
    MeteoraTheme {
        WindCard(
            windSpeed = weatherInfo.windSpeed.toInt(),
            windDirection = weatherInfo.windDirection,
            windGusts = weatherInfo.windGusts.toInt(),
            modifier = Modifier.aspectRatio(ratio = 2f),
        )
    }
}
