@file:Suppress("UnusedReceiverParameter")

package org.meteora.presentation.icon.weather

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val WeatherIcons.Sunset: ImageVector
    get() {
        if (_Sunset != null) {
            return _Sunset!!
        }
        _Sunset = ImageVector.Builder(
            name = "Sunset",
            defaultWidth = 96.dp,
            defaultHeight = 90.dp,
            viewportWidth = 96f,
            viewportHeight = 90f
        ).apply {
            path(fill = SolidColor(Color(0xFFFAD631))) {
                moveTo(56.438f, 40.852f)
                curveToRelative(5.647f, 3.144f, 9.385f, 8.02f, 11.156f, 14.23f)
                curveTo(68.356f, 59.61f, 67.539f, 62.774f, 66f, 67f)
                arcToRelative(
                    6590f,
                    6590f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    -15.753f,
                    0.055f
                )
                quadToRelative(-2.682f, 0.008f, -5.363f, 0.02f)
                curveToRelative(-2.565f, 0.013f, -5.129f, 0.018f, -7.693f, 0.023f)
                lineToRelative(-2.436f, 0.015f)
                curveToRelative(-1.919f, 0f, -3.837f, -0.051f, -5.755f, -0.113f)
                curveToRelative(-1.838f, -1.838f, -1.301f, -4.588f, -1.375f, -7.062f)
                curveToRelative(0.07f, -6.328f, 0.954f, -10.344f, 5.375f, -14.938f)
                curveToRelative(6.366f, -6.126f, 15.192f, -7.03f, 23.438f, -4.148f)
            }
            path(fill = SolidColor(Color.White)) {
                moveToRelative(7.9f, 70.876f)
                lineToRelative(2.567f, 0.01f)
                horizontalLineToRelative(2.69f)
                curveToRelative(2.95f, 0f, 5.898f, 0.009f, 8.847f, 0.016f)
                lineToRelative(6.116f, 0.005f)
                quadToRelative(8.067f, 0.006f, 16.133f, 0.024f)
                quadToRelative(8.224f, 0.014f, 16.448f, 0.02f)
                quadTo(76.851f, 70.967f, 93f, 71f)
                arcToRelative(88f, 88f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1f, 5f)
                curveToRelative(-1.845f, 1.845f, -4.445f, 1.12f, -6.9f, 1.124f)
                lineToRelative(-2.567f, -0.01f)
                horizontalLineToRelative(-2.69f)
                curveToRelative(-2.95f, 0f, -5.898f, -0.009f, -8.847f, -0.016f)
                lineToRelative(-6.116f, -0.005f)
                quadToRelative(-8.067f, -0.006f, -16.133f, -0.024f)
                quadToRelative(-8.224f, -0.014f, -16.448f, -0.02f)
                quadTo(18.149f, 77.033f, 2f, 77f)
                arcToRelative(88f, 88f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1f, -5f)
                curveToRelative(1.845f, -1.845f, 4.445f, -1.12f, 6.9f, -1.124f)
            }
            path(fill = SolidColor(Color(0xFFBFBFBF))) {
                moveTo(46f, 1f)
                curveToRelative(3.536f, 0.536f, 3.536f, 0.536f, 5f, 2f)
                curveToRelative(0.237f, 2.859f, 0.422f, 5.699f, 0.563f, 8.563f)
                lineToRelative(0.13f, 2.443f)
                quadTo(51.852f, 17.003f, 52f, 20f)
                lineToRelative(2.324f, -2.512f)
                lineTo(57f, 15f)
                curveToRelative(2.813f, 0.188f, 2.813f, 0.188f, 5f, 1f)
                curveToRelative(-0.12f, 3.972f, -1.555f, 5.85f, -4.25f, 8.563f)
                arcTo(257f, 257f, 0f, isMoreThanHalf = false, isPositiveArc = true, 54f, 28f)
                lineToRelative(-2.687f, 2.5f)
                curveTo(49f, 32f, 49f, 32f, 47.25f, 31.922f)
                curveToRelative(-3.394f, -1.39f, -5.882f, -3.737f, -8.562f, -6.172f)
                lineToRelative(-1.706f, -1.45f)
                curveTo(35.387f, 22.86f, 35.387f, 22.86f, 33f, 20f)
                curveToRelative(0.145f, -2.86f, 0.145f, -2.86f, 1f, -5f)
                curveToRelative(4.374f, 0.486f, 6.039f, 1.836f, 9f, 5f)
                lineToRelative(0.148f, -3.648f)
                lineToRelative(0.227f, -4.727f)
                lineToRelative(0.094f, -2.406f)
                curveTo(43.773f, 3.227f, 43.773f, 3.227f, 46f, 1f)
            }
            path(fill = SolidColor(Color(0xFFFAD631))) {
                moveTo(18f, 29f)
                curveToRelative(2.063f, -0.437f, 2.063f, -0.437f, 5f, 0f)
                curveToRelative(2.791f, 1.99f, 5.022f, 4.2f, 7f, 7f)
                curveToRelative(0.438f, 3.438f, 0.438f, 3.438f, 0f, 6f)
                curveToRelative(-2.562f, 0.5f, -2.562f, 0.5f, -6f, 0f)
                curveToRelative(-2.854f, -2.3f, -5.239f, -4.776f, -7f, -8f)
                curveToRelative(0.309f, -2.77f, 0.309f, -2.77f, 1f, -5f)
                moveTo(13.438f, 54.813f)
                lineToRelative(3.558f, -0.043f)
                curveTo(20f, 55f, 20f, 55f, 22f, 57f)
                curveToRelative(-0.375f, 2.625f, -0.375f, 2.625f, -1f, 5f)
                quadToRelative(-3.469f, 0.039f, -6.937f, 0.063f)
                lineToRelative(-3.903f, 0.035f)
                curveTo(7f, 62f, 7f, 62f, 6f, 61f)
                curveToRelative(-0.125f, -2.5f, -0.125f, -2.5f, 0f, -5f)
                curveToRelative(1.872f, -1.872f, 4.937f, -1.174f, 7.438f, -1.187f)
                moveTo(78.004f, 54.77f)
                lineToRelative(3.558f, 0.042f)
                lineToRelative(3.567f, 0.02f)
                curveTo(88f, 55f, 88f, 55f, 89f, 56f)
                curveToRelative(0.125f, 2.5f, 0.125f, 2.5f, 0f, 5f)
                curveToRelative(-1f, 1f, -1f, 1f, -4.16f, 1.098f)
                lineToRelative(-3.903f, -0.035f)
                lineToRelative(-3.91f, -0.028f)
                lineTo(74f, 62f)
                curveToRelative(-0.625f, -2.375f, -0.625f, -2.375f, -1f, -5f)
                curveToRelative(2f, -2f, 2f, -2f, 5.004f, -2.23f)
                moveTo(75.773f, 29.273f)
                lineTo(78f, 30f)
                curveToRelative(0.438f, 2.063f, 0.438f, 2.063f, 0f, 5f)
                arcToRelative(
                    31.5f,
                    31.5f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    -7f,
                    7f
                )
                curveToRelative(-2.937f, 0.438f, -2.937f, 0.438f, -5f, 0f)
                curveToRelative(-0.727f, -2.227f, -0.727f, -2.227f, -1f, -5f)
                curveToRelative(1.477f, -2.273f, 1.477f, -2.273f, 3.625f, -4.375f)
                lineToRelative(2.102f, -2.148f)
                curveTo(73f, 29f, 73f, 29f, 75.773f, 29.273f)
            }
        }.build()

        return _Sunset!!
    }

@Suppress("ObjectPropertyName")
private var _Sunset: ImageVector? = null
