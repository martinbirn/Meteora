@file:Suppress("UnusedReceiverParameter")

package org.meteora.presentation.icon.weather

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val WeatherIcons.Sunrise: ImageVector
    get() {
        if (_Sunrise != null) {
            return _Sunrise!!
        }
        _Sunrise = ImageVector.Builder(
            name = "Sunrise",
            defaultWidth = 96.dp,
            defaultHeight = 90.dp,
            viewportWidth = 96f,
            viewportHeight = 90f
        ).apply {
            path(fill = SolidColor(Color(0xFFFAD631))) {
                moveTo(56.438f, 40.852f)
                curveTo(61.408f, 43.62f, 65.16f, 47.579f, 67f, 53f)
                curveToRelative(0.436f, 4.373f, 0.45f, 8.627f, 0f, 13f)
                curveToRelative(-1.816f, 1.816f, -4.313f, 1.129f, -6.729f, 1.139f)
                lineToRelative(-2.427f, -0.006f)
                lineToRelative(-2.477f, 0.003f)
                quadToRelative(-2.598f, 0f, -5.196f, -0.006f)
                quadToRelative(-3.99f, -0.005f, -7.98f, 0.007f)
                quadToRelative(-2.517f, 0f, -5.035f, -0.004f)
                lineToRelative(-2.427f, 0.006f)
                curveTo(29.115f, 67.115f, 29.115f, 67.115f, 28f, 66f)
                curveToRelative(-0.723f, -7.036f, -1.026f, -13.532f, 3.406f, -19.344f)
                curveToRelative(6.855f, -7.124f, 15.475f, -9.143f, 25.032f, -5.804f)
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
                moveTo(47.992f, 0.852f)
                curveToRelative(3.097f, 1.771f, 5.694f, 4.012f, 8.32f, 6.398f)
                lineToRelative(1.706f, 1.45f)
                curveToRelative(1.595f, 1.44f, 1.595f, 1.44f, 3.982f, 4.3f)
                curveToRelative(-0.145f, 2.86f, -0.145f, 2.86f, -1f, 5f)
                curveToRelative(-4.374f, -0.486f, -6.039f, -1.836f, -9f, -5f)
                lineToRelative(-0.148f, 3.648f)
                lineToRelative(-0.227f, 4.727f)
                lineToRelative(-0.094f, 2.406f)
                curveTo(51.227f, 29.773f, 51.227f, 29.773f, 49f, 32f)
                curveToRelative(-3.536f, -0.536f, -3.536f, -0.536f, -5f, -2f)
                arcToRelative(
                    254f,
                    254f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    -0.562f,
                    -8.562f
                )
                lineToRelative(-0.131f, -2.444f)
                arcTo(1763f, 1763f, 0f, isMoreThanHalf = false, isPositiveArc = true, 43f, 13f)
                lineToRelative(-2.324f, 2.512f)
                lineTo(38f, 18f)
                curveToRelative(-2.812f, -0.187f, -2.812f, -0.187f, -5f, -1f)
                curveToRelative(0.33f, -5.146f, 3.165f, -7.62f, 6.813f, -10.875f)
                lineToRelative(1.619f, -1.547f)
                curveToRelative(3.874f, -3.526f, 3.874f, -3.526f, 6.56f, -3.726f)
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
                moveTo(75.77f, 29.273f)
                lineTo(78f, 30f)
                curveToRelative(0.438f, 2.063f, 0.438f, 2.063f, 0f, 5f)
                curveToRelative(-1.99f, 2.791f, -4.2f, 5.022f, -7f, 7f)
                curveToRelative(-3.437f, 0.438f, -3.437f, 0.438f, -6f, 0f)
                curveToRelative(-0.329f, -3.177f, -0.273f, -4.605f, 1.574f, -7.273f)
                lineToRelative(2.114f, -2.102f)
                lineToRelative(2.074f, -2.148f)
                curveTo(73f, 29f, 73f, 29f, 75.77f, 29.273f)
            }
        }.build()

        return _Sunrise!!
    }

@Suppress("ObjectPropertyName")
private var _Sunrise: ImageVector? = null
