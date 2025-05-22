@file:Suppress("UnusedReceiverParameter")

package org.meteora.presentation.icon.weather

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val WeatherIcons.FreezingRain: ImageVector
    get() {
        if (_FreezingRain != null) {
            return _FreezingRain!!
        }
        _FreezingRain = ImageVector.Builder(
            name = "FreezingRain",
            defaultWidth = 96.dp,
            defaultHeight = 90.dp,
            viewportWidth = 96f,
            viewportHeight = 90f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(60f, 4f)
                curveToRelative(4f, 3.135f, 7.087f, 5.862f, 8f, 11f)
                lineToRelative(3f, -0.25f)
                curveToRelative(5.277f, 0.021f, 9.647f, 2.135f, 13.547f, 5.64f)
                curveToRelative(4.404f, 4.923f, 5.92f, 9.975f, 5.695f, 16.532f)
                curveToRelative(-0.776f, 6.66f, -3.224f, 10.686f, -8.426f, 14.851f)
                curveToRelative(-3.967f, 2.68f, -6.76f, 3.481f, -11.59f, 3.518f)
                lineToRelative(-3.52f, 0.038f)
                lineToRelative(-3.792f, 0.007f)
                lineToRelative(-3.908f, 0.02f)
                quadToRelative(-4.093f, 0.017f, -8.186f, 0.015f)
                curveToRelative(-3.486f, 0f, -6.97f, 0.027f, -10.457f, 0.061f)
                curveToRelative(-3.337f, 0.028f, -6.674f, 0.027f, -10.011f, 0.029f)
                lineToRelative(-3.78f, 0.043f)
                curveTo(15.82f, 55.448f, 15.82f, 55.448f, 11f, 51f)
                curveToRelative(-3.921f, -4.334f, -4.642f, -7.876f, -4.379f, -13.54f)
                curveTo(7.208f, 33.653f, 8.428f, 30.88f, 11f, 28f)
                arcToRelative(
                    72f,
                    72f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    3.563f,
                    -2.375f
                )
                curveToRelative(3.846f, -2.486f, 4.467f, -4.99f, 5.64f, -9.281f)
                curveTo(22.377f, 9.949f, 27.111f, 5.124f, 33f, 2f)
                curveToRelative(9.286f, -3.571f, 18.378f, -2.946f, 27f, 2f)
            }
            path(fill = SolidColor(Color.White)) {
                moveTo(41f, 76f)
                verticalLineToRelative(3f)
                lineToRelative(4f, -1f)
                lineToRelative(-0.562f, 2.25f)
                curveToRelative(-0.43f, 2.7f, -0.29f, 4.196f, 0.562f, 6.75f)
                lineToRelative(-4f, -1f)
                verticalLineToRelative(3f)
                horizontalLineToRelative(-4f)
                lineToRelative(-1f, -2f)
                horizontalLineToRelative(-3f)
                lineToRelative(0.5f, -2.25f)
                curveToRelative(0.62f, -2.94f, 0.62f, -2.94f, 0.5f, -6.75f)
                curveToRelative(2.647f, -1.46f, 3.894f, -2f, 7f, -2f)
                moveTo(21f, 70f)
                horizontalLineToRelative(3f)
                verticalLineToRelative(3f)
                horizontalLineToRelative(4f)
                lineToRelative(-0.562f, 1.75f)
                curveToRelative(-0.478f, 2.457f, -0.195f, 3.894f, 0.562f, 6.25f)
                horizontalLineToRelative(-3f)
                lineToRelative(-2f, 3f)
                horizontalLineToRelative(-2f)
                verticalLineToRelative(-4f)
                lineToRelative(-5f, 1f)
                verticalLineToRelative(-3f)
                horizontalLineToRelative(2f)
                verticalLineToRelative(-2f)
                horizontalLineToRelative(-2f)
                verticalLineToRelative(-3f)
                horizontalLineToRelative(4f)
                close()
                moveTo(59f, 70f)
                horizontalLineToRelative(2f)
                lineToRelative(1f, 3f)
                horizontalLineToRelative(4f)
                verticalLineToRelative(3f)
                horizontalLineToRelative(-2f)
                verticalLineToRelative(2f)
                horizontalLineToRelative(2f)
                verticalLineToRelative(3f)
                horizontalLineToRelative(-4f)
                lineToRelative(-1f, 3f)
                lineToRelative(-3f, -1f)
                lineToRelative(-1f, -2f)
                horizontalLineToRelative(-3f)
                lineToRelative(0.563f, -1.75f)
                curveToRelative(0.477f, -2.457f, 0.194f, -3.894f, -0.563f, -6.25f)
                horizontalLineToRelative(3f)
                close()
            }
            path(fill = SolidColor(Color(0xFF36C8EB))) {
                moveToRelative(46f, 60f)
                lineToRelative(5f, 1f)
                curveToRelative(-0.56f, 3.99f, -1.757f, 6.526f, -4.062f, 9.813f)
                lineToRelative(-1.66f, 2.394f)
                lineTo(44f, 75f)
                lineToRelative(-4f, -1f)
                curveToRelative(0.428f, -5.706f, 2.843f, -9.41f, 6f, -14f)
                moveTo(65f, 60f)
                lineToRelative(5f, 1f)
                curveToRelative(-0.48f, 4.314f, -1.746f, 6.166f, -5f, 9f)
                lineToRelative(-3f, -1f)
                curveToRelative(0.498f, -3.735f, 0.875f, -5.812f, 3f, -9f)
                moveTo(27f, 60f)
                lineToRelative(5f, 1f)
                curveToRelative(-0.53f, 3.821f, -1.497f, 6.062f, -4f, 9f)
                lineToRelative(-4f, -2f)
                curveToRelative(0.628f, -2.929f, 1.586f, -5.361f, 3f, -8f)
            }
        }.build()

        return _FreezingRain!!
    }

@Suppress("ObjectPropertyName")
private var _FreezingRain: ImageVector? = null
