package org.meteora.presentation.screen.weather.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.meteora.data.util.toRadians
import org.meteora.presentation.theme.MeteoraColor
import org.meteora.presentation.theme.MeteoraTheme
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun WindCompass(
    windSpeed: Int,
    windDirection: Int,
    modifier: Modifier = Modifier,
) {
    val letterStyle = MaterialTheme.typography.labelSmall.copy(
        color = MeteoraColor.White50,
        fontWeight = FontWeight.Bold,
        lineHeight = MaterialTheme.typography.labelSmall.fontSize,
    )
    val textMeasurer = rememberTextMeasurer()
    val textLayoutResult = textMeasurer.measure(
        text = "N",
        style = letterStyle
    )

    val letterHeight = textLayoutResult.size.height.toFloat() - 6f
    val letterHeightDp = with(LocalDensity.current) {
        letterHeight.toDp()
    }

    Box(
        modifier = modifier
            .aspectRatio(1f),
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "N",
                modifier = Modifier.align(alignment = Alignment.TopCenter),
                style = letterStyle,
            )
            Text(
                text = "E",
                modifier = Modifier
                    .align(alignment = Alignment.CenterEnd)
                    .width(width = letterHeightDp),
                textAlign = TextAlign.Center,
                style = letterStyle,
            )
            Text(
                text = "S",
                modifier = Modifier.align(alignment = Alignment.BottomCenter),
                style = letterStyle,
            )
            Text(
                text = "W",
                modifier = Modifier
                    .align(alignment = Alignment.CenterStart)
                    .width(width = letterHeightDp),
                textAlign = TextAlign.Center,
                style = letterStyle,
            )
        }

        Canvas(modifier = Modifier.fillMaxSize()) {
            val radius = size.minDimension / 2f
            val center = Offset(size.width / 2, size.height / 2)

            val totalSticks = 108
            val angleBetweenSticks = 360f / totalSticks

            var currentAngle = 0f
            var stickIndex = 0

            while (stickIndex < totalSticks) {
                val angleRad = currentAngle.toDouble().toRadians().toFloat()

                val mod27 = stickIndex % 27
                if (mod27 == 25 || mod27 == 26 || mod27 == 0 || mod27 == 1 || mod27 == 2) {
                    currentAngle += angleBetweenSticks
                    stickIndex++
                    continue
                }

                val isLongStick = stickIndex % 9 == 0
                val strokeWidth = if (isLongStick) 2f else 1f

                val start = Offset(
                    x = center.x + cos(angleRad) * (radius - letterHeight),
                    y = center.y + sin(angleRad) * (radius - letterHeight)
                )
                val end = Offset(
                    x = center.x + cos(angleRad) * radius,
                    y = center.y + sin(angleRad) * radius
                )

                drawLine(
                    color = MeteoraColor.White50,
                    start = start,
                    end = end,
                    strokeWidth = strokeWidth
                )

                currentAngle += angleBetweenSticks
                stickIndex++
            }

            val angleRad = (windDirection + 90).toDouble().toRadians().toFloat()

            val letterRadius = letterHeight / 2
            drawLine(
                color = MeteoraColor.White,
                start = Offset(
                    x = center.x + cos(angleRad) * (radius - letterRadius),
                    y = center.y + sin(angleRad) * (radius - letterRadius)
                ),
                end = Offset(
                    x = center.x + cos(angleRad + PI.toFloat()) * radius,
                    y = center.y + sin(angleRad + PI.toFloat()) * radius
                ),
                strokeWidth = 6f
            )

            drawCircle(
                color = MeteoraColor.White,
                radius = letterRadius,
                center = Offset(
                    x = center.x + cos(angleRad + PI.toFloat()) * (radius - letterRadius),
                    y = center.y + sin(angleRad + PI.toFloat()) * (radius - letterRadius)
                )
            )

            val arrowPath = Path().apply {
                moveTo(0f, 0f)
                lineTo(letterHeight, letterRadius)
                lineTo(0f, letterHeight)
                lineTo(letterRadius, letterRadius)
                close()
            }

            withTransform({
                translate(center.x + cos(angleRad) * radius, center.y + sin(angleRad) * radius)
                translate(-letterHeight, -letterRadius)
                rotate((windDirection + 90).toFloat(), pivot = Offset(letterHeight, letterRadius))
            }) {
                drawPath(
                    path = arrowPath,
                    color = MeteoraColor.White
                )
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "$windSpeed",
                style = MaterialTheme.typography.labelLarge.copy(
                    color = MeteoraColor.White
                )
            )
            Text(
                text = "m/s",
                style = MaterialTheme.typography.labelMedium.copy(
                    color = MeteoraColor.White
                )
            )
        }
    }
}

@Preview
@Composable
private fun PreviewWindCompas() {
    MeteoraTheme {
        WindCompass(
            windSpeed = 5,
            windDirection = 47,
            modifier = Modifier
                .width(100.dp)
                .background(color = MeteoraColor.Black, shape = CircleShape)
        )
    }
}
