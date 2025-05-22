@file:Suppress("UnusedReceiverParameter")

package org.meteora.presentation.icon.weather

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val WeatherIcons.Thunderstorm: ImageVector
    get() {
        if (_Thunderstorm != null) {
            return _Thunderstorm!!
        }
        _Thunderstorm = ImageVector.Builder(
            name = "Thunderstorm",
            defaultWidth = 96.dp,
            defaultHeight = 90.dp,
            viewportWidth = 96f,
            viewportHeight = 90f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(58.805f, 5.367f)
                curveToRelative(2.993f, 2.227f, 5.044f, 4.936f, 7.347f, 7.844f)
                curveToRelative(2.792f, 2.703f, 5.78f, 3.268f, 9.485f, 4.156f)
                curveTo(81.135f, 18.84f, 84.953f, 22.32f, 88f, 27f)
                curveToRelative(2.374f, 5.766f, 2.445f, 11.004f, 1f, 17f)
                curveToRelative(-2.407f, 5.614f, -5.584f, 9.057f, -11f, 12f)
                curveToRelative(-3.102f, 1.034f, -5.3f, 1.145f, -8.554f, 1.177f)
                lineToRelative(-3.45f, 0.039f)
                lineToRelative(-3.73f, 0.022f)
                lineToRelative(-3.838f, 0.025f)
                quadToRelative(-4.026f, 0.021f, -8.05f, 0.032f)
                curveToRelative(-3.424f, 0.011f, -6.848f, 0.046f, -10.272f, 0.085f)
                curveToRelative(-3.28f, 0.033f, -6.56f, 0.037f, -9.84f, 0.046f)
                lineToRelative(-3.707f, 0.053f)
                curveTo(19.813f, 57.462f, 15.445f, 57.12f, 10f, 53f)
                curveToRelative(-3.477f, -4.056f, -4.13f, -8.782f, -4f, -14f)
                curveToRelative(0.847f, -4.594f, 2.495f, -7.888f, 6f, -11f)
                curveToRelative(3f, -2f, 3f, -2f, 6f, -2f)
                lineToRelative(0.125f, -3.437f)
                curveToRelative(0.84f, -6.413f, 4.194f, -11.346f, 9.133f, -15.43f)
                curveTo(36.877f, 0.498f, 48.396f, -0.49f, 58.805f, 5.367f)
            }
            path(fill = SolidColor(Color.White)) {
                moveToRelative(44.918f, 61.512f)
                lineToRelative(3.27f, 0.175f)
                lineToRelative(3.292f, 0.137f)
                lineTo(54f, 62f)
                lineToRelative(-2f, 6f)
                lineToRelative(7f, 1f)
                curveToRelative(-0.58f, 3.616f, -1.934f, 5.32f, -4.492f, 7.89f)
                lineToRelative(-2.137f, 2.15f)
                lineToRelative(-2.246f, 2.21f)
                lineToRelative(-2.223f, 2.258f)
                lineToRelative(-2.144f, 2.133f)
                lineToRelative(-1.965f, 1.954f)
                curveTo(42f, 89f, 42f, 89f, 39f, 89f)
                curveToRelative(0.665f, -4.389f, 2.188f, -7.981f, 4f, -12f)
                lineToRelative(-7f, -1f)
                quadToRelative(0.647f, -2.097f, 1.313f, -4.187f)
                lineToRelative(0.738f, -2.356f)
                curveToRelative(2.807f, -7.266f, 2.807f, -7.266f, 6.867f, -7.945f)
            }
            path(fill = SolidColor(Color(0xFF36C8EB))) {
                moveToRelative(27f, 72f)
                lineToRelative(5f, 1f)
                curveToRelative(-0.576f, 4.755f, -2.55f, 7.97f, -5f, 12f)
                horizontalLineToRelative(-5f)
                curveToRelative(-0.684f, -1.703f, -0.684f, -1.703f, -1f, -4f)
                curveToRelative(1.215f, -2.36f, 1.215f, -2.36f, 2.938f, -4.75f)
                lineToRelative(1.714f, -2.422f)
                close()
                moveTo(70f, 62f)
                lineToRelative(5f, 1f)
                curveToRelative(-0.473f, 4.964f, -1.736f, 8.192f, -5f, 12f)
                curveToRelative(-2.75f, 0.375f, -2.75f, 0.375f, -5f, 0f)
                curveToRelative(0.45f, -5.625f, 1.684f, -8.615f, 5f, -13f)
                moveTo(77f, 72f)
                lineToRelative(5f, 1f)
                curveToRelative(-0.576f, 4.755f, -2.55f, 7.97f, -5f, 12f)
                horizontalLineToRelative(-5f)
                curveToRelative(0.45f, -5.625f, 1.684f, -8.615f, 5f, -13f)
                moveTo(20f, 62f)
                quadToRelative(2.514f, 0.428f, 5f, 1f)
                curveToRelative(-0.576f, 4.755f, -2.55f, 7.97f, -5f, 12f)
                horizontalLineToRelative(-5f)
                curveToRelative(-1f, -3f, -1f, -3f, -0.04f, -5.227f)
                curveToRelative(0.468f, -0.791f, 0.934f, -1.583f, 1.415f, -2.398f)
                lineToRelative(1.398f, -2.414f)
                curveTo(19f, 63f, 19f, 63f, 20f, 62f)
            }
        }.build()

        return _Thunderstorm!!
    }

@Suppress("ObjectPropertyName")
private var _Thunderstorm: ImageVector? = null
