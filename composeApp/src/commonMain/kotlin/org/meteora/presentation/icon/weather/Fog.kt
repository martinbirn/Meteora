@file:Suppress("UnusedReceiverParameter")

package org.meteora.presentation.icon.weather

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val WeatherIcons.Fog: ImageVector
    get() {
        if (_Fog != null) {
            return _Fog!!
        }
        _Fog = ImageVector.Builder(
            name = "Fog",
            defaultWidth = 96.dp,
            defaultHeight = 90.dp,
            viewportWidth = 96f,
            viewportHeight = 90f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(60.754f, 8.102f)
                curveToRelative(2.716f, 2.11f, 4.357f, 4.094f, 6.059f, 7.086f)
                curveToRelative(2.792f, 3.59f, 4.63f, 3.614f, 8.945f, 4.335f)
                curveTo(81.809f, 20.81f, 85.483f, 25.277f, 89f, 30f)
                curveToRelative(1.96f, 5.88f, 1.828f, 12.603f, -0.187f, 18.438f)
                curveTo(85.27f, 53.446f, 81.768f, 56.822f, 76f, 59f)
                curveToRelative(-4.426f, 0.457f, -8.835f, 0.444f, -13.281f, 0.434f)
                lineToRelative(-3.9f, 0.015f)
                quadToRelative(-4.074f, 0.013f, -8.15f, -0.003f)
                curveToRelative(-3.47f, -0.011f, -6.939f, 0.009f, -10.41f, 0.038f)
                curveToRelative(-3.325f, 0.023f, -6.651f, 0.016f, -9.978f, 0.012f)
                lineToRelative(-3.76f, 0.034f)
                curveTo(15.834f, 59.444f, 15.834f, 59.444f, 11f, 55f)
                curveToRelative(-3.228f, -3.86f, -4.34f, -8.013f, -4.187f, -13f)
                curveToRelative(0.83f, -5.66f, 3.866f, -9.387f, 8.187f, -13f)
                lineToRelative(3f, -1f)
                curveToRelative(0.998f, -2.293f, 0.998f, -2.293f, 1.75f, -5.062f)
                curveToRelative(2.465f, -7.218f, 6.357f, -12.422f, 12.938f, -16.375f)
                curveToRelative(8.995f, -4.244f, 19.56f, -3.804f, 28.066f, 1.539f)
                moveTo(24f, 79f)
                horizontalLineToRelative(50f)
                lineToRelative(1f, 5f)
                horizontalLineTo(23f)
                close()
                moveTo(26.056f, 66.88f)
                lineToRelative(2.74f, 0.006f)
                horizontalLineToRelative(3.093f)
                lineToRelative(3.365f, 0.016f)
                lineToRelative(3.426f, 0.005f)
                curveToRelative(3.627f, 0.005f, 7.255f, 0.018f, 10.883f, 0.03f)
                quadToRelative(3.678f, 0.008f, 7.357f, 0.014f)
                quadToRelative(9.04f, 0.017f, 18.08f, 0.049f)
                verticalLineToRelative(5f)
                horizontalLineTo(24f)
                lineToRelative(-1f, -4f)
                curveToRelative(1f, -1f, 1f, -1f, 3.056f, -1.12f)
            }
        }.build()

        return _Fog!!
    }

@Suppress("ObjectPropertyName")
private var _Fog: ImageVector? = null
