package org.meteora.presentation.component.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.meteora.domain.entity.WeatherInfo
import org.meteora.presentation.icon.MeteoraIcons
import org.meteora.presentation.icon.Sun
import org.meteora.presentation.resources.Res
import org.meteora.presentation.resources.extreme
import org.meteora.presentation.resources.high
import org.meteora.presentation.resources.low
import org.meteora.presentation.resources.moderate
import org.meteora.presentation.resources.use_sun_protection_until
import org.meteora.presentation.resources.uv_index
import org.meteora.presentation.resources.very_high
import org.meteora.presentation.theme.MeteoraColor
import org.meteora.presentation.theme.MeteoraTheme
import org.meteora.presentation.util.preview.WeatherInfoParameters

@Composable
fun UvIndexCard(
    uvIndex: Int,
    sunProtectionUntil: String,
    modifier: Modifier = Modifier,
) {
    SquareContainer(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                imageVector = MeteoraIcons.Sun,
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
                        color = MeteoraColor.White,
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

@Preview
@Composable
private fun PreviewUvIndexCard(
    // @PreviewParameter is broken on my AS version (https://youtrack.jetbrains.com/issue/KMT-879)
    // @PreviewParameter(WeatherInfoParameters::class)
    weatherInfo: WeatherInfo = WeatherInfoParameters().values.first()
) {
    MeteoraTheme {
        UvIndexCard(
            uvIndex = weatherInfo.main.uvIndex.toInt(),
            sunProtectionUntil = "16:00",
            modifier = Modifier.width(width = 200.dp)
        )
    }
}
