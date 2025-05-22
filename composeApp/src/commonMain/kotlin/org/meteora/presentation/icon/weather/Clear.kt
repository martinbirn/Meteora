@file:Suppress("UnusedReceiverParameter")

package org.meteora.presentation.icon.weather

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val WeatherIcons.Clear: ImageVector
    get() {
        if (_Clear != null) {
            return _Clear!!
        }
        _Clear = ImageVector.Builder(
            name = "Clear",
            defaultWidth = 96.dp,
            defaultHeight = 90.dp,
            viewportWidth = 96f,
            viewportHeight = 90f
        ).apply {
            path(fill = SolidColor(Color(0xFFFAD631))) {
                moveTo(58.176f, 28.734f)
                curveTo(62.912f, 32.02f, 65.903f, 36.298f, 67f, 42f)
                curveToRelative(0.083f, 6.379f, -0.739f, 11.028f, -5f, 16f)
                curveToRelative(-4.86f, 4.486f, -9.162f, 6.515f, -15.883f, 6.313f)
                curveToRelative(-5.24f, -0.774f, -9.512f, -3.301f, -12.992f, -7.25f)
                curveTo(29.508f, 51.85f, 28.056f, 47.393f, 29f, 41f)
                curveToRelative(2.223f, -6.845f, 6.11f, -10.85f, 12.375f, -14.312f)
                curveToRelative(6.343f, -1.662f, 11.146f, -1.109f, 16.8f, 2.046f)
                moveTo(45f, 70f)
                horizontalLineToRelative(6f)
                curveToRelative(1.227f, 2.454f, 1.202f, 4.2f, 1.25f, 6.938f)
                lineToRelative(0.078f, 2.652f)
                curveTo(51.943f, 82.42f, 51.193f, 83.25f, 49f, 85f)
                lineToRelative(-5f, -2f)
                arcToRelative(
                    888f,
                    888f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    -0.062f,
                    -5.375f
                )
                lineToRelative(-0.036f, -3.023f)
                curveTo(44f, 72f, 44f, 72f, 45f, 70f)
                moveTo(80f, 40.875f)
                lineToRelative(2.688f, -0.008f)
                curveTo(85f, 41f, 85f, 41f, 87f, 42f)
                verticalLineToRelative(6f)
                curveToRelative(-2.454f, 1.227f, -4.2f, 1.202f, -6.937f, 1.25f)
                lineToRelative(-2.653f, 0.078f)
                curveTo(75f, 49f, 75f, 49f, 73.172f, 47.618f)
                curveTo(72f, 46f, 72f, 46f, 72.188f, 43.874f)
                curveToRelative(1.548f, -3.575f, 4.328f, -3.01f, 7.812f, -3f)
                moveTo(73.938f, 16.063f)
                lineTo(76f, 17f)
                lineToRelative(1f, 5f)
                arcToRelative(
                    242f,
                    242f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    -3.75f,
                    3.563f
                )
                lineToRelative(-2.11f, 2.003f)
                curveTo(69f, 29f, 69f, 29f, 66.735f, 28.715f)
                curveTo(65f, 28f, 65f, 28f, 64.063f, 25.938f)
                curveToRelative(-0.077f, -3.615f, 0.66f, -4.223f, 3.062f, -6.813f)
                curveToRelative(3.38f, -3.136f, 3.38f, -3.136f, 6.813f, -3.062f)
                moveTo(23.25f, 16f)
                curveToRelative(4.117f, 1.497f, 6.152f, 4.58f, 8.75f, 8f)
                lineToRelative(-2f, 5f)
                curveToRelative(-4.562f, -0.268f, -6.825f, -2.175f, -9.84f, -5.367f)
                curveTo(19f, 22f, 19f, 22f, 18.625f, 19.125f)
                lineTo(19f, 17f)
                curveToRelative(2f, -1f, 2f, -1f, 4.25f, -1f)
                moveTo(69.313f, 61.625f)
                curveTo(72.633f, 63.324f, 74.685f, 65.073f, 77f, 68f)
                curveToRelative(-0.187f, 2.813f, -0.187f, 2.813f, -1f, 5f)
                curveToRelative(-2.062f, 0.938f, -2.062f, 0.938f, -5f, 1f)
                curveToRelative(-2.844f, -1.944f, -5.046f, -4.164f, -7f, -7f)
                curveToRelative(-0.062f, -2.875f, -0.062f, -2.875f, 1f, -5f)
                curveToRelative(2f, -1f, 2f, -1f, 4.313f, -0.375f)
                moveTo(28.938f, 61.063f)
                curveTo(31f, 62f, 31f, 62f, 31.69f, 63.73f)
                curveTo(32f, 66f, 32f, 66f, 30.777f, 68.176f)
                curveTo(28.937f, 70.314f, 27.217f, 72.249f, 25f, 74f)
                curveToRelative(-3.437f, 0f, -3.437f, 0f, -6f, -1f)
                curveToRelative(-0.437f, -2.062f, -0.437f, -2.062f, 0f, -5f)
                curveToRelative(2.676f, -3.727f, 5.155f, -7.04f, 9.938f, -6.937f)
                moveTo(16f, 40.875f)
                lineToRelative(2.688f, -0.008f)
                curveTo(21f, 41f, 21f, 41f, 23f, 42f)
                verticalLineToRelative(6f)
                curveToRelative(-2.488f, 1.244f, -4.227f, 1.133f, -7f, 1.125f)
                lineToRelative(-2.687f, 0.008f)
                curveTo(11f, 49f, 11f, 49f, 9f, 48f)
                verticalLineToRelative(-6f)
                curveToRelative(2.488f, -1.244f, 4.227f, -1.133f, 7f, -1.125f)
                moveTo(45f, 6f)
                horizontalLineToRelative(6f)
                curveToRelative(1.244f, 2.488f, 1.133f, 4.227f, 1.125f, 7f)
                lineToRelative(0.008f, 2.688f)
                curveTo(52f, 18f, 52f, 18f, 51f, 20f)
                horizontalLineToRelative(-6f)
                curveToRelative(-1.244f, -2.488f, -1.133f, -4.227f, -1.125f, -7f)
                lineToRelative(-0.008f, -2.687f)
                curveTo(44f, 8f, 44f, 8f, 45f, 6f)
            }
        }.build()

        return _Clear!!
    }

@Suppress("ObjectPropertyName")
private var _Clear: ImageVector? = null
