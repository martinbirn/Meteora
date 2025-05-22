@file:Suppress("UnusedReceiverParameter")

package org.meteora.presentation.icon.weather

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val WeatherIcons.Haze: ImageVector
    get() {
        if (_Haze != null) {
            return _Haze!!
        }
        _Haze = ImageVector.Builder(
            name = "Haze",
            defaultWidth = 96.dp,
            defaultHeight = 90.dp,
            viewportWidth = 96f,
            viewportHeight = 90f
        ).apply {
            path(fill = SolidColor(Color(0xFFFAD631))) {
                moveTo(56.695f, 24.074f)
                curveToRelative(4.436f, 2.672f, 7.679f, 5.768f, 9.727f, 10.563f)
                curveTo(67.575f, 39.35f, 67.469f, 44.192f, 67f, 49f)
                curveToRelative(-1.816f, 1.816f, -4.313f, 1.129f, -6.729f, 1.139f)
                lineToRelative(-2.427f, -0.006f)
                lineToRelative(-2.477f, 0.003f)
                quadToRelative(-2.598f, 0f, -5.196f, -0.006f)
                curveToRelative(-2.66f, -0.005f, -5.32f, 0f, -7.98f, 0.007f)
                quadToRelative(-2.517f, 0f, -5.035f, -0.004f)
                lineToRelative(-2.427f, 0.006f)
                curveTo(29.115f, 50.115f, 29.115f, 50.115f, 28f, 49f)
                curveToRelative(-0.723f, -7.036f, -1.026f, -13.532f, 3.406f, -19.344f)
                curveToRelative(6.895f, -7.165f, 15.772f, -9.375f, 25.29f, -5.582f)
            }
            path(fill = SolidColor(Color.White)) {
                moveToRelative(11.986f, 66.74f)
                lineToRelative(2.094f, 0.017f)
                lineToRelative(2.208f, -0.006f)
                curveToRelative(2.414f, -0.003f, 4.829f, 0.007f, 7.243f, 0.019f)
                horizontalLineToRelative(5.022f)
                quadToRelative(5.265f, 0.003f, 10.531f, 0.024f)
                quadToRelative(6.76f, 0.023f, 13.519f, 0.02f)
                quadToRelative(5.186f, 0.001f, 10.371f, 0.013f)
                lineToRelative(4.986f, 0.006f)
                quadToRelative(3.48f, 0.004f, 6.96f, 0.022f)
                lineToRelative(2.094f, -0.002f)
                curveToRelative(4.757f, 0.033f, 4.757f, 0.033f, 6.986f, 1.147f)
                lineToRelative(-1f, 5f)
                horizontalLineTo(6f)
                lineToRelative(-1f, -4f)
                curveToRelative(2.424f, -2.424f, 3.68f, -2.238f, 6.986f, -2.26f)
                moveTo(16.947f, 80.876f)
                lineToRelative(2.143f, 0.01f)
                horizontalLineToRelative(2.247f)
                curveToRelative(2.462f, 0f, 4.924f, 0.009f, 7.386f, 0.016f)
                lineToRelative(5.107f, 0.005f)
                quadToRelative(6.735f, 0.006f, 13.47f, 0.024f)
                quadToRelative(6.866f, 0.014f, 13.733f, 0.02f)
                quadTo(74.517f, 80.967f, 88f, 81f)
                arcToRelative(88f, 88f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1f, 5f)
                curveToRelative(-1.673f, 1.673f, -3.714f, 1.12f, -5.947f, 1.124f)
                lineToRelative(-2.143f, -0.01f)
                horizontalLineToRelative(-2.247f)
                curveToRelative(-2.462f, 0f, -4.924f, -0.009f, -7.386f, -0.016f)
                lineToRelative(-5.107f, -0.005f)
                quadToRelative(-6.735f, -0.006f, -13.47f, -0.024f)
                quadToRelative(-6.866f, -0.014f, -13.733f, -0.02f)
                quadTo(25.483f, 87.033f, 12f, 87f)
                arcToRelative(88f, 88f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1f, -5f)
                curveToRelative(1.673f, -1.673f, 3.714f, -1.12f, 5.947f, -1.124f)
                moveTo(11f, 54f)
                horizontalLineToRelative(78f)
                verticalLineToRelative(5f)
                horizontalLineTo(12f)
                close()
            }
            path(fill = SolidColor(Color(0xFFFAD631))) {
                moveTo(74.813f, 11.563f)
                curveTo(77f, 12f, 77f, 12f, 78f, 13f)
                curveToRelative(0.5f, 2.125f, 0.5f, 2.125f, 0f, 5f)
                curveToRelative(-2.513f, 3.273f, -5.203f, 6.33f, -9.125f, 7.793f)
                lineTo(67f, 26f)
                curveToRelative(-1.75f, -1.437f, -1.75f, -1.437f, -3f, -3f)
                curveToRelative(1.58f, -5.371f, 5.038f, -10.54f, 10.813f, -11.437f)
                moveTo(7f, 38f)
                curveToRelative(2.969f, -0.363f, 2.969f, -0.363f, 6.5f, -0.312f)
                lineToRelative(3.531f, 0.011f)
                curveTo(20f, 38f, 20f, 38f, 22f, 40f)
                lineToRelative(-1f, 5f)
                horizontalLineTo(7f)
                lineToRelative(-2f, -4f)
                close()
                moveTo(77.75f, 37.77f)
                lineToRelative(3.25f, 0.042f)
                lineToRelative(3.25f, 0.02f)
                curveTo(87f, 38f, 87f, 38f, 89f, 39f)
                curveToRelative(0.043f, 1.666f, 0.04f, 3.334f, 0f, 5f)
                curveToRelative(-1f, 1f, -1f, 1f, -4.16f, 1.098f)
                lineToRelative(-3.903f, -0.035f)
                lineToRelative(-3.91f, -0.028f)
                lineTo(74f, 45f)
                curveToRelative(-0.625f, -2.375f, -0.625f, -2.375f, -1f, -5f)
                curveToRelative(2f, -2f, 2f, -2f, 4.75f, -2.23f)
                moveTo(47.563f, 0.938f)
                lineTo(50f, 1f)
                curveToRelative(1.363f, 2.725f, 1.17f, 4.96f, 1.188f, 8f)
                lineToRelative(0.042f, 3.25f)
                curveTo(51f, 15f, 51f, 15f, 49f, 17f)
                curveToRelative(-2.625f, -0.375f, -2.625f, -0.375f, -5f, -1f)
                quadToRelative(-0.04f, -3.468f, -0.062f, -6.937f)
                lineToRelative(-0.036f, -3.903f)
                curveToRelative(0.128f, -4.136f, 0.128f, -4.136f, 3.66f, -4.222f)
                moveTo(18f, 12f)
                curveToRelative(3.974f, 0f, 6.064f, 1.664f, 8.875f, 4.25f)
                curveToRelative(3.135f, 3.284f, 3.135f, 3.284f, 3.063f, 6.688f)
                curveToRelative(-0.31f, 0.68f, -0.62f, 1.36f, -0.938f, 2.062f)
                lineToRelative(-3f, 1f)
                curveToRelative(-2.008f, -1.398f, -2.008f, -1.398f, -4.125f, -3.375f)
                lineToRelative(-2.133f, -1.96f)
                curveToRelative(-2.74f, -2.618f, -2.74f, -2.618f, -2.867f, -5.165f)
                curveTo(17f, 13f, 17f, 13f, 18f, 12f)
            }
        }.build()

        return _Haze!!
    }

@Suppress("ObjectPropertyName")
private var _Haze: ImageVector? = null
