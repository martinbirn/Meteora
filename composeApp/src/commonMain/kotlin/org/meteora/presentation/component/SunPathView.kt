package org.meteora.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.meteora.presentation.theme.MeteoraColor
import org.meteora.presentation.theme.MeteoraTheme
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun SunPathView(
    sunProgress: Float, // 0.0f (sunrise) to 1.0f (sunset)
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(100.dp)
            .fillMaxWidth()
            .drawBehind {
                val width = size.width
                val height = size.height

                val centerY = height / 2
                val radiusX = width / 2
                val radiusY = centerY

                // Horizontal line (horizon)
                drawLine(
                    color = Color.White.copy(alpha = 0.5f),
                    start = Offset(0f, centerY),
                    end = Offset(width, centerY),
                    strokeWidth = 2.dp.toPx()
                )

                // Day/Night arc
                val steps = 100
                val path = Path()

                for (i in 0..steps) {
                    val progress = i / steps.toFloat()
                    val angle = PI * progress

                    val x = radiusX + radiusX * cos(angle).toFloat()
                    val y = centerY - radiusY * sin(angle).toFloat()

                    if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
                }

                // Gradient on top (day), bottom (night)
                drawPath(
                    path = path,
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.White.copy(alpha = 0.6f), // Top of arc (day)
                            Color.Black.copy(alpha = 0.2f)  // Bottom of arc (night)
                        ),
                        startY = 0f,
                        endY = height
                    ),
                    style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round)
                )

                // Sun / Moon coordinates
                val sunAngle = PI * (1 - sunProgress)
                val sunX = radiusX + radiusX * cos(sunAngle).toFloat()
                val sunY = centerY - radiusY * sin(sunAngle).toFloat()

                // Glow (aura)
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = if (sunY < centerY) {
                            listOf(
                                Color.Yellow.copy(alpha = 0.5f),
                                Color.Transparent
                            )
                        } else {
                            listOf(
                                Color.Black.copy(alpha = 0.3f),
                                Color.Transparent
                            )
                        },
                        center = Offset(sunX, sunY),
                        radius = 16.dp.toPx()
                    ),
                    center = Offset(sunX, sunY),
                    radius = 16.dp.toPx()
                )

                // Sun / Moon circle
                drawCircle(
                    color = if (sunY < centerY) Color.Yellow else Color.Black,
                    radius = 6.dp.toPx(),
                    center = Offset(sunX, sunY)
                )
            }
    )
}

@Preview
@Composable
private fun PreviewSunPathView() {
    MeteoraTheme {
        Box(modifier = Modifier.background(color = MeteoraColor.Black)) {
            SunPathView(sunProgress = 0.8f)
        }
    }
}