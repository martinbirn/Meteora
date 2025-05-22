@file:Suppress("UnusedReceiverParameter")

package org.meteora.presentation.icon.weather

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val WeatherIcons.Cloudy: ImageVector
    get() {
        if (_Cloudy != null) {
            return _Cloudy!!
        }
        _Cloudy = ImageVector.Builder(
            name = "Cloudy",
            defaultWidth = 96.dp,
            defaultHeight = 90.dp,
            viewportWidth = 96f,
            viewportHeight = 90f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(63f, 18f)
                curveToRelative(2.856f, 2.693f, 5.241f, 5.482f, 7f, 9f)
                curveToRelative(2.086f, 0.467f, 2.086f, 0.467f, 4.563f, 0.75f)
                curveToRelative(6.439f, 1.162f, 10.945f, 4.22f, 15.082f, 9.25f)
                curveToRelative(3.561f, 5.255f, 3.636f, 10.583f, 2.82f, 16.645f)
                curveTo(91.155f, 60.278f, 87.456f, 64.303f, 82f, 68f)
                curveToRelative(-3.747f, 1.767f, -6.728f, 2.26f, -10.857f, 2.29f)
                lineToRelative(-3.5f, 0.04f)
                lineToRelative(-3.756f, 0.006f)
                lineToRelative(-3.89f, 0.02f)
                quadToRelative(-4.071f, 0.017f, -8.142f, 0.015f)
                curveToRelative(-3.46f, 0f, -6.92f, 0.027f, -10.38f, 0.061f)
                curveToRelative(-3.32f, 0.028f, -6.642f, 0.027f, -9.963f, 0.029f)
                lineToRelative(-3.738f, 0.043f)
                curveTo(20.709f, 70.467f, 15.965f, 69.895f, 10f, 66f)
                curveToRelative(-4.032f, -4.94f, -4.638f, -9.09f, -4.387f, -15.414f)
                curveToRelative(0.749f, -5.004f, 3.36f, -7.634f, 6.95f, -10.961f)
                curveTo(15f, 38f, 15f, 38f, 18f, 38f)
                lineToRelative(0.188f, -3.5f)
                curveToRelative(1.034f, -7.93f, 5.906f, -13.156f, 11.898f, -18.094f)
                curveTo(40.015f, 9.112f, 53.368f, 11.485f, 63f, 18f)
            }
        }.build()

        return _Cloudy!!
    }

@Suppress("ObjectPropertyName")
private var _Cloudy: ImageVector? = null
