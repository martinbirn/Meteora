@file:Suppress("UnusedReceiverParameter")

package org.meteora.presentation.icon.weather

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val WeatherIcons.PartlyCloudy: ImageVector
    get() {
        if (_PartlyCloudy != null) {
            return _PartlyCloudy!!
        }
        _PartlyCloudy = ImageVector.Builder(
            name = "PartlyCloudy",
            defaultWidth = 108.dp,
            defaultHeight = 90.dp,
            viewportWidth = 108f,
            viewportHeight = 90f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveToRelative(50f, 34f)
                lineToRelative(2.38f, 3.13f)
                curveToRelative(3.23f, 3.53f, 5.61f, 3.9f, 10.12f, 5.03f)
                curveToRelative(4.59f, 1.54f, 8.08f, 4.6f, 10.67f, 8.67f)
                curveToRelative(1.92f, 4.98f, 1.77f, 11.33f, 0.11f, 16.37f)
                curveTo(70.68f, 72.04f, 67.19f, 75.27f, 62f, 77f)
                curveToRelative(-1.67f, 0.11f, -3.35f, 0.16f, -5.02f, 0.18f)
                lineToRelative(-3.1f, 0.04f)
                lineToRelative(-3.34f, 0.02f)
                lineToRelative(-3.44f, 0.03f)
                quadToRelative(-3.61f, 0.02f, -7.22f, 0.03f)
                curveToRelative(-3.67f, 0.02f, -7.35f, 0.06f, -11.02f, 0.1f)
                quadToRelative(-3.51f, 0.01f, -7.01f, 0.03f)
                lineToRelative(-3.33f, 0.05f)
                curveTo(12.39f, 77.46f, 8.62f, 77.22f, 4f, 73f)
                curveTo(1.13f, 68.28f, 0.52f, 64.5f, 1.36f, 59.15f)
                curveTo(2.45f, 55.49f, 4.24f, 53.63f, 7f, 51f)
                lineToRelative(2.09f, -0.9f)
                curveToRelative(2.07f, -0.93f, 2.07f, -0.93f, 2.71f, -2.98f)
                lineToRelative(0.51f, -2.24f)
                curveToRelative(1.96f, -6.61f, 5.96f, -11.15f, 11.75f, -14.81f)
                curveTo(33.52f, 26.64f, 42.2f, 27.5f, 50f, 34f)
            }
            path(fill = SolidColor(Color(0xFFFAD631))) {
                moveTo(78.44f, 25.63f)
                curveToRelative(5.48f, 5.08f, 8.25f, 8.59f, 8.87f, 16.13f)
                curveToRelative(-0.08f, 5.88f, -2.7f, 10.35f, -6.44f, 14.75f)
                curveTo(79f, 58f, 79f, 58f, 77f, 58f)
                lineToRelative(-0.48f, -1.76f)
                curveTo(74.87f, 50.71f, 73.2f, 47.01f, 69f, 43f)
                curveToRelative(-3.38f, -1.72f, -6.61f, -2.89f, -10.25f, -3.94f)
                curveTo(55.61f, 37.85f, 54.4f, 37.06f, 53f, 34f)
                curveToRelative(0.9f, -4.49f, 3.32f, -6.48f, 7f, -9f)
                curveToRelative(5.99f, -2f, 12.72f, -2.08f, 18.44f, 0.63f)
                moveTo(93.94f, 14.06f)
                lineTo(96f, 15f)
                curveToRelative(0.44f, 2.06f, 0.44f, 2.06f, 0f, 5f)
                curveToRelative(-2f, 2.78f, -4.19f, 5.04f, -7f, 7f)
                curveToRelative(-2.94f, -0.06f, -2.94f, -0.06f, -5f, -1f)
                curveToRelative(-0.44f, -2.06f, -0.44f, -2.06f, 0f, -5f)
                curveToRelative(2.68f, -3.73f, 5.16f, -7.04f, 9.94f, -6.94f)
                moveTo(85f, 57f)
                curveToRelative(4.53f, 0.13f, 6.86f, 2.22f, 9.84f, 5.37f)
                curveTo(96f, 64f, 96f, 64f, 96.38f, 66.88f)
                lineTo(96f, 69f)
                curveToRelative(-2.06f, 0.94f, -2.06f, 0.94f, -5f, 1f)
                curveToRelative(-2.81f, -1.96f, -5f, -4.22f, -7f, -7f)
                curveToRelative(-0.5f, -2.88f, -0.5f, -2.88f, 0f, -5f)
                close()
                moveTo(43f, 14f)
                curveToRelative(4.06f, 0.58f, 6.05f, 2.5f, 8.81f, 5.37f)
                curveTo(53f, 21f, 53f, 21f, 53.38f, 23.88f)
                lineTo(53f, 26f)
                curveToRelative(-2.06f, 0.94f, -2.06f, 0.94f, -5f, 1f)
                curveToRelative(-2.84f, -1.94f, -5.05f, -4.16f, -7f, -7f)
                curveToRelative(0f, -2.73f, 0.05f, -4.05f, 2f, -6f)
                moveTo(67f, 4f)
                lineToRelative(5f, 1f)
                verticalLineToRelative(13f)
                lineToRelative(-5f, 1f)
                curveToRelative(-2f, -2f, -2f, -2f, -2.27f, -4.53f)
                verticalLineTo(8.53f)
                curveTo(65f, 6f, 65f, 6f, 67f, 4f)
                moveTo(99f, 37.88f)
                lineToRelative(2.69f, -0.01f)
                curveTo(104f, 38f, 104f, 38f, 106f, 39f)
                curveToRelative(0.04f, 1.67f, 0.04f, 3.33f, 0f, 5f)
                curveToRelative(-1f, 1f, -1f, 1f, -3.94f, 1.1f)
                lineToRelative(-3.62f, -0.04f)
                lineToRelative(-3.63f, -0.03f)
                lineTo(92f, 45f)
                verticalLineToRelative(-6f)
                curveToRelative(2.49f, -1.24f, 4.23f, -1.13f, 7f, -1.13f)
            }
        }.build()

        return _PartlyCloudy!!
    }

@Suppress("ObjectPropertyName")
private var _PartlyCloudy: ImageVector? = null
