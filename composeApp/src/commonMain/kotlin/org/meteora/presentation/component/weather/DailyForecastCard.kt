package org.meteora.presentation.component.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.meteora.domain.entity.DailyWeatherInfo
import org.meteora.domain.entity.WeatherInfo
import org.meteora.presentation.icon.Calendar
import org.meteora.presentation.icon.MeteoraIcons
import org.meteora.presentation.resources.Res
import org.meteora.presentation.resources.ten_day_forecast
import org.meteora.presentation.resources.today
import org.meteora.presentation.theme.MeteoraColor
import org.meteora.presentation.theme.MeteoraTheme
import org.meteora.presentation.util.icon
import org.meteora.presentation.util.preview.WeatherInfoParameters

@Composable
fun DailyForecastCard(
    todayTemp: Int,
    dailies: List<DailyWeatherInfo>,
    modifier: Modifier = Modifier,
) {
    val globalMin = remember(dailies) { dailies.minOf { it.tempMin }.toInt() }
    val globalMax = remember(dailies) { dailies.maxOf { it.tempMax }.toInt() }
    BlurredContainer(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                imageVector = MeteoraIcons.Calendar,
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
                    modifier = Modifier
                        .weight(weight = 1f)
                        .padding(vertical = 12.dp),
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1,
                    style = MaterialTheme.typography.bodyLarge
                )
                Image(
                    imageVector = daily.weatherCode.icon,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(width = 16.dp))
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

@Preview
@Composable
private fun PreviewDailyForecastCard(
    // @PreviewParameter is broken on my AS version (https://youtrack.jetbrains.com/issue/KMT-879)
    // @PreviewParameter(WeatherInfoParameters::class)
    weatherInfo: WeatherInfo = WeatherInfoParameters().values.first()
) {
    MeteoraTheme {
        DailyForecastCard(
            todayTemp = weatherInfo.main.temp.toInt(),
            dailies = weatherInfo.dailies
        )
    }
}
