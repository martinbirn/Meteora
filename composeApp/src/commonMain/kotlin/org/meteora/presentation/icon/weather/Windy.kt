@file:Suppress("UnusedReceiverParameter")

package org.meteora.presentation.icon.weather

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val WeatherIcons.Windy: ImageVector
    get() {
        if (_Windy != null) {
            return _Windy!!
        }
        _Windy = ImageVector.Builder(
            name = "Windy",
            defaultWidth = 96.dp,
            defaultHeight = 90.dp,
            viewportWidth = 96f,
            viewportHeight = 90f
        ).apply {
            path(fill = SolidColor(Color(0xFFA6A6A6))) {
                moveTo(79f, 27f)
                curveToRelative(3.204f, 2.523f, 5.732f, 5.081f, 7f, 9f)
                curveToRelative(0.456f, 5.016f, 0.13f, 8.007f, -3f, 12f)
                curveToRelative(-3.738f, 3.45f, -6.358f, 4.94f, -11.426f, 5.055f)
                curveToRelative(-6.521f, -0.406f, -12.548f, -1.456f, -18.896f, -2.968f)
                curveTo(38.329f, 46.672f, 26.448f, 46.423f, 12f, 50f)
                lineToRelative(-1f, -5f)
                curveToRelative(15.141f, -6.829f, 31.824f, -4.936f, 47.539f, -0.779f)
                curveToRelative(6.094f, 1.602f, 11.628f, 2.664f, 17.899f, 1.217f)
                curveToRelative(1.893f, -1.496f, 1.893f, -1.496f, 2.562f, -4.813f)
                curveToRelative(0.31f, -3.593f, 0.31f, -3.593f, -1.5f, -6.125f)
                curveToRelative(-2.524f, -1.789f, -2.524f, -1.789f, -6.062f, -1.625f)
                curveToRelative(-3.688f, 0.778f, -3.688f, 0.778f, -5.126f, 4.125f)
                lineTo(65f, 40f)
                curveToRelative(-2.125f, 0.938f, -2.125f, 0.938f, -4f, 1f)
                lineToRelative(-1f, -1f)
                curveToRelative(0.115f, -6.908f, 0.115f, -6.908f, 2.3f, -9.605f)
                curveTo(67.7f, 25.826f, 72.348f, 25.28f, 79f, 27f)
                moveTo(56.125f, 59f)
                curveToRelative(2.847f, 4.554f, 2.962f, 8.785f, 1.875f, 14f)
                curveToRelative(-1.711f, 2.906f, -2.831f, 3.36f, -6f, 5f)
                curveToRelative(-4.232f, 0.908f, -7.226f, 0.494f, -10.937f, -1.875f)
                curveToRelative(-2.629f, -2.708f, -3.05f, -3.446f, -3.126f, -7.25f)
                lineTo(38f, 66f)
                lineToRelative(5f, -1f)
                lineToRelative(3f, 6f)
                horizontalLineToRelative(6f)
                curveToRelative(0.062f, -3.411f, 0.062f, -3.411f, -1f, -7f)
                curveToRelative(-5.729f, -3.377f, -10.97f, -3.428f, -17.5f, -3.312f)
                lineToRelative(-2.762f, -0.01f)
                curveTo(25.414f, 60.72f, 21.062f, 61.4f, 16f, 63f)
                curveToRelative(-2.937f, -0.375f, -2.937f, -0.375f, -5f, -1f)
                lineToRelative(1f, -5f)
                curveToRelative(13.651f, -4.578f, 31.549f, -6.049f, 44.125f, 2f)
            }
            path(fill = SolidColor(Color(0xFFA6A6A6))) {
                moveTo(54f, 14f)
                curveToRelative(2.866f, 2.448f, 4.613f, 4.249f, 5.441f, 8f)
                curveToRelative(0.217f, 3.693f, -0.333f, 5.83f, -2.316f, 8.938f)
                curveToRelative(-2.342f, 2.273f, -3.228f, 3.03f, -6.472f, 3.144f)
                lineToRelative(-2.93f, -0.055f)
                lineToRelative(-3.248f, -0.037f)
                quadToRelative(-3.36f, -0.051f, -6.72f, -0.117f)
                curveToRelative(-6.318f, -0.064f, -12.14f, 0.338f, -18.32f, 1.692f)
                curveToRelative(-2.528f, 0.452f, -4.872f, 0.508f, -7.435f, 0.435f)
                lineToRelative(-1f, -5f)
                curveToRelative(8.634f, -3.461f, 17.006f, -3.354f, 26.125f, -3.187f)
                quadToRelative(2.172f, 0.022f, 4.344f, 0.04f)
                curveToRelative(3.51f, 0.033f, 7.02f, 0.085f, 10.531f, 0.147f)
                verticalLineToRelative(-7f)
                curveToRelative(-2.293f, -0.86f, -2.293f, -0.86f, -5f, -1f)
                arcToRelative(
                    181f,
                    181f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -4f,
                    4f
                )
                curveToRelative(-2.25f, -0.25f, -2.25f, -0.25f, -4f, -1f)
                curveToRelative(-0.25f, -2.25f, -0.25f, -2.25f, 0f, -5f)
                curveToRelative(4.138f, -5.137f, 8.773f, -5.938f, 15f, -4f)
            }
        }.build()

        return _Windy!!
    }

@Suppress("ObjectPropertyName")
private var _Windy: ImageVector? = null
