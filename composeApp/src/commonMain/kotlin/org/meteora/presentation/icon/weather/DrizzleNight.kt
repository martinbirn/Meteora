@file:Suppress("UnusedReceiverParameter")

package org.meteora.presentation.icon.weather

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val WeatherIcons.DrizzleNight: ImageVector
    get() {
        if (_DrizzleNight != null) {
            return _DrizzleNight!!
        }
        _DrizzleNight = ImageVector.Builder(
            name = "DrizzleNight",
            defaultWidth = 96.dp,
            defaultHeight = 90.dp,
            viewportWidth = 96f,
            viewportHeight = 90f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(45.5f, 15.625f)
                curveToRelative(3.697f, 2.193f, 6.056f, 4.861f, 8.5f, 8.375f)
                verticalLineToRelative(2f)
                lineToRelative(3.563f, 0.25f)
                curveToRelative(5.137f, 0.673f, 9.331f, 2.236f, 12.726f, 6.29f)
                curveToRelative(3.113f, 4.903f, 4.266f, 10.022f, 3.34f, 15.757f)
                curveTo(72.035f, 54.199f, 69.143f, 57.727f, 64f, 61f)
                curveToRelative(-2.928f, 0.976f, -4.874f, 1.143f, -7.927f, 1.177f)
                lineToRelative(-3.035f, 0.039f)
                lineToRelative(-3.276f, 0.022f)
                lineToRelative(-3.375f, 0.025f)
                quadToRelative(-3.536f, 0.02f, -7.073f, 0.032f)
                curveToRelative(-3.602f, 0.017f, -7.203f, 0.061f, -10.804f, 0.105f)
                quadToRelative(-3.436f, 0.015f, -6.873f, 0.026f)
                lineToRelative(-3.258f, 0.053f)
                curveTo(12.269f, 62.46f, 8.616f, 62.144f, 4f, 58f)
                curveTo(0.984f, 53.577f, 0.085f, 49.268f, 1f, 44f)
                curveToRelative(1.363f, -3.68f, 2.925f, -6.435f, 6.5f, -8.312f)
                curveToRelative(3.542f, -2.392f, 4.109f, -5.337f, 5.559f, -9.254f)
                curveToRelative(2.345f, -6.063f, 6.297f, -8.808f, 12.023f, -11.48f)
                curveToRelative(6.638f, -2.17f, 13.987f, -2.205f, 20.418f, 0.671f)
            }
            path(fill = SolidColor(Color.White)) {
                moveTo(73f, 1f)
                curveToRelative(-0.504f, 3.53f, -1.003f, 7.012f, -1.75f, 10.5f)
                curveToRelative(-0.318f, 4.447f, 0.416f, 6.708f, 2.75f, 10.5f)
                curveToRelative(5.31f, 3.332f, 9.156f, 3.192f, 15f, 2f)
                lineToRelative(3.5f, -0.562f)
                lineTo(95f, 23f)
                curveToRelative(1.613f, 4.033f, 0.457f, 7.047f, -1f, 11f)
                curveToRelative(-3.097f, 5.42f, -8.354f, 7.785f, -14f, 10f)
                horizontalLineToRelative(-3f)
                lineToRelative(-0.445f, -1.941f)
                curveTo(74.958f, 35.902f, 73.19f, 30.932f, 68f, 27f)
                curveToRelative(-3.051f, -1.481f, -6.1f, -2.719f, -9.293f, -3.86f)
                curveTo(56.085f, 22.037f, 54.669f, 21.28f, 53f, 19f)
                curveToRelative(-0.127f, -6.456f, 2.792f, -10.443f, 7f, -15f)
                curveTo(64.224f, 0.443f, 67.7f, 0.73f, 73f, 1f)
            }
            path(fill = SolidColor(Color(0xFF36C8EB))) {
                moveToRelative(16f, 67f)
                lineToRelative(5f, 1f)
                curveToRelative(-0.473f, 4.964f, -1.736f, 8.192f, -5f, 12f)
                curveToRelative(-2.75f, 0.375f, -2.75f, 0.375f, -5f, 0f)
                curveToRelative(0.45f, -5.625f, 1.684f, -8.615f, 5f, -13f)
                moveTo(27f, 77f)
                lineToRelative(5f, 1f)
                curveToRelative(-0.473f, 4.964f, -1.736f, 8.192f, -5f, 12f)
                curveToRelative(-2.75f, 0.375f, -2.75f, 0.375f, -5f, 0f)
                curveToRelative(-0.839f, -5.367f, 2.08f, -8.796f, 5f, -13f)
                moveTo(44f, 67f)
                lineToRelative(5f, 1f)
                curveToRelative(-0.473f, 4.964f, -1.736f, 8.192f, -5f, 12f)
                curveToRelative(-2.75f, 0.375f, -2.75f, 0.375f, -5f, 0f)
                curveToRelative(-0.839f, -5.367f, 2.08f, -8.796f, 5f, -13f)
                moveTo(55f, 77f)
                lineToRelative(5f, 1f)
                curveToRelative(-0.476f, 5f, -2.032f, 7.978f, -5f, 12f)
                lineToRelative(-5f, -1f)
                curveToRelative(0.576f, -4.755f, 2.55f, -7.97f, 5f, -12f)
            }
        }.build()

        return _DrizzleNight!!
    }

@Suppress("ObjectPropertyName")
private var _DrizzleNight: ImageVector? = null
