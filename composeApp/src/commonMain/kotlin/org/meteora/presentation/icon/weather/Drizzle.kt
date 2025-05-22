@file:Suppress("UnusedReceiverParameter")

package org.meteora.presentation.icon.weather

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val WeatherIcons.Drizzle: ImageVector
    get() {
        if (_Drizzle != null) {
            return _Drizzle!!
        }
        _Drizzle = ImageVector.Builder(
            name = "Drizzle",
            defaultWidth = 96.dp,
            defaultHeight = 90.dp,
            viewportWidth = 96f,
            viewportHeight = 90f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(57.629f, 5.52f)
                curveTo(61.902f, 8.188f, 65.149f, 11.874f, 68f, 16f)
                verticalLineToRelative(2f)
                lineToRelative(3f, -0.25f)
                curveToRelative(4.677f, 0.038f, 8.237f, 1.507f, 12f, 4.25f)
                curveToRelative(5.242f, 5.537f, 7.364f, 10.155f, 7.207f, 17.852f)
                curveToRelative(-0.547f, 5.678f, -2.352f, 9.728f, -6.644f, 13.523f)
                curveToRelative(-4.382f, 3.509f, -7.67f, 4.873f, -13.338f, 4.916f)
                lineToRelative(-3.52f, 0.038f)
                lineToRelative(-3.79f, 0.007f)
                lineToRelative(-3.909f, 0.02f)
                quadToRelative(-4.093f, 0.017f, -8.186f, 0.015f)
                curveToRelative(-3.486f, 0f, -6.97f, 0.027f, -10.457f, 0.061f)
                curveToRelative(-3.337f, 0.028f, -6.674f, 0.027f, -10.011f, 0.029f)
                lineToRelative(-3.78f, 0.043f)
                curveTo(15.83f, 58.448f, 15.83f, 58.448f, 11f, 54f)
                curveToRelative(-3.43f, -3.7f, -4.3f, -6.58f, -4.5f, -11.5f)
                curveToRelative(0.2f, -4.92f, 1.07f, -7.8f, 4.5f, -11.5f)
                lineToRelative(2.938f, -1.625f)
                curveToRelative(4.107f, -3.185f, 4.964f, -6.63f, 6.792f, -11.395f)
                curveToRelative(2.832f, -6.648f, 6.93f, -10.301f, 13.438f, -13.253f)
                curveToRelative(7.778f, -3.085f, 15.844f, -2.583f, 23.46f, 0.793f)
            }
            path(fill = SolidColor(Color(0xFF36C8EB))) {
                moveTo(54f, 63f)
                horizontalLineToRelative(5f)
                curveToRelative(-0.45f, 5.625f, -1.684f, 8.615f, -5f, 13f)
                lineToRelative(-5f, -1f)
                curveToRelative(0.576f, -4.755f, 2.55f, -7.97f, 5f, -12f)
                moveTo(37f, 73f)
                lineToRelative(5f, 1f)
                curveToRelative(-0.576f, 4.755f, -2.55f, 7.97f, -5f, 12f)
                horizontalLineToRelative(-5f)
                curveToRelative(0.515f, -5.281f, 2.255f, -8.572f, 5f, -13f)
                moveTo(26f, 63f)
                lineToRelative(5f, 1f)
                curveToRelative(-0.428f, 4.707f, -2.552f, 8.775f, -6f, 12f)
                horizontalLineToRelative(-4f)
                curveToRelative(0.45f, -5.625f, 1.684f, -8.615f, 5f, -13f)
                moveTo(66f, 73f)
                horizontalLineToRelative(4f)
                curveToRelative(-0.45f, 5.625f, -1.684f, 8.615f, -5f, 13f)
                lineToRelative(-5f, -1f)
                curveToRelative(1.314f, -4.6f, 2.976f, -8.264f, 6f, -12f)
            }
        }.build()

        return _Drizzle!!
    }

@Suppress("ObjectPropertyName")
private var _Drizzle: ImageVector? = null
