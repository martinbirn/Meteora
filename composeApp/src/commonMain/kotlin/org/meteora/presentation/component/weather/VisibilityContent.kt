package org.meteora.presentation.component.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.meteora.domain.entity.WeatherInfo
import org.meteora.presentation.icon.Eye
import org.meteora.presentation.icon.MeteoraIcons
import org.meteora.presentation.resources.Res
import org.meteora.presentation.resources.visibility
import org.meteora.presentation.theme.MeteoraColor
import org.meteora.presentation.theme.MeteoraTheme
import org.meteora.presentation.util.preview.WeatherInfoParameters

@Composable
fun VisibilityCard(
    visibility: Double,
    modifier: Modifier = Modifier
) {
    SquareContainer(modifier = modifier) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                imageVector = MeteoraIcons.Eye,
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


@Preview
@Composable
private fun PreviewVisibilityCard(
    // @PreviewParameter is broken on my AS version (https://youtrack.jetbrains.com/issue/KMT-879)
    // @PreviewParameter(WeatherInfoParameters::class)
    weatherInfo: WeatherInfo = WeatherInfoParameters().values.first()
) {
    MeteoraTheme {
        VisibilityCard(
            visibility = weatherInfo.visibility,
            modifier = Modifier.width(width = 200.dp)
        )
    }
}
