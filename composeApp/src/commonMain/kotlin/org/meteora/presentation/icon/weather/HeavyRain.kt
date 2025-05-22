@file:Suppress("UnusedReceiverParameter")

package org.meteora.presentation.icon.weather

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val WeatherIcons.HeavyRain: ImageVector
    get() {
        if (_HeavyRain != null) {
            return _HeavyRain!!
        }
        _HeavyRain = ImageVector.Builder(
            name = "HeavyRain",
            defaultWidth = 96.dp,
            defaultHeight = 90.dp,
            viewportWidth = 96f,
            viewportHeight = 90f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(60.754f, 6.102f)
                curveToRelative(2.716f, 2.11f, 4.357f, 4.094f, 6.059f, 7.086f)
                curveToRelative(2.792f, 3.59f, 4.63f, 3.614f, 8.945f, 4.335f)
                curveTo(81.809f, 18.81f, 85.483f, 23.277f, 89f, 28f)
                curveToRelative(1.96f, 5.88f, 1.828f, 12.603f, -0.187f, 18.438f)
                curveTo(85.27f, 51.446f, 81.768f, 54.822f, 76f, 57f)
                curveToRelative(-4.426f, 0.457f, -8.835f, 0.444f, -13.281f, 0.434f)
                lineToRelative(-3.9f, 0.015f)
                quadToRelative(-4.074f, 0.013f, -8.15f, -0.003f)
                curveToRelative(-3.47f, -0.011f, -6.939f, 0.009f, -10.41f, 0.038f)
                curveToRelative(-3.325f, 0.023f, -6.651f, 0.016f, -9.978f, 0.012f)
                lineToRelative(-3.76f, 0.034f)
                curveTo(15.834f, 57.444f, 15.834f, 57.444f, 11f, 53f)
                curveToRelative(-3.228f, -3.86f, -4.34f, -8.013f, -4.187f, -13f)
                curveToRelative(0.83f, -5.66f, 3.866f, -9.387f, 8.187f, -13f)
                lineToRelative(3f, -1f)
                curveToRelative(0.998f, -2.293f, 0.998f, -2.293f, 1.75f, -5.062f)
                curveToRelative(2.465f, -7.218f, 6.357f, -12.422f, 12.938f, -16.375f)
                curveTo(41.683f, 0.319f, 52.248f, 0.759f, 60.754f, 6.102f)
            }
            path(fill = SolidColor(Color(0xFF36C8EB))) {
                moveToRelative(39f, 62f)
                lineToRelative(5f, 1f)
                curveToRelative(-0.527f, 5.269f, -3.224f, 9.297f, -5.875f, 13.75f)
                lineToRelative(-1.355f, 2.363f)
                lineToRelative(-1.325f, 2.246f)
                lineToRelative(-1.202f, 2.046f)
                curveTo(33f, 85f, 33f, 85f, 30f, 86f)
                lineToRelative(-2f, -1f)
                curveToRelative(0.443f, -5.896f, 3.037f, -9.965f, 6.063f, -14.875f)
                lineToRelative(1.431f, -2.375f)
                arcTo(772f, 772f, 0f, isMoreThanHalf = false, isPositiveArc = true, 39f, 62f)
                moveTo(25f, 62f)
                lineToRelative(5f, 1f)
                curveToRelative(-1.028f, 4.862f, -3.44f, 8.865f, -5.812f, 13.188f)
                lineToRelative(-1.33f, 2.521f)
                lineToRelative(-1.315f, 2.396f)
                lineToRelative(-1.187f, 2.193f)
                curveTo(19f, 85f, 19f, 85f, 16.904f, 85.806f)
                lineTo(15f, 86f)
                curveToRelative(-1f, -1f, -1f, -1f, -1.213f, -3.286f)
                curveToRelative(0.241f, -3.072f, 0.982f, -4.554f, 2.62f, -7.14f)
                lineToRelative(1.503f, -2.412f)
                lineToRelative(1.59f, -2.475f)
                lineToRelative(1.59f, -2.533f)
                arcTo(766f, 766f, 0f, isMoreThanHalf = false, isPositiveArc = true, 25f, 62f)
                moveTo(54f, 62f)
                horizontalLineToRelative(4f)
                curveToRelative(0.825f, 5.435f, -2.055f, 9.224f, -4.75f, 13.688f)
                lineToRelative(-1.344f, 2.337f)
                curveTo(48.213f, 84.245f, 48.213f, 84.245f, 44f, 86f)
                curveToRelative(-1.12f, -1.734f, -1.12f, -1.734f, -2f, -4f)
                curveToRelative(0.66f, -2.075f, 0.66f, -2.075f, 1.922f, -4.258f)
                lineToRelative(1.36f, -2.394f)
                lineToRelative(1.468f, -2.473f)
                lineToRelative(1.422f, -2.496f)
                curveTo(51.639f, 64.36f, 51.639f, 64.36f, 54f, 62f)
                moveTo(68f, 62f)
                horizontalLineToRelative(4f)
                curveToRelative(-0.452f, 5.908f, -2.205f, 9.815f, -5.312f, 14.813f)
                lineToRelative(-1.186f, 2.021f)
                curveTo(63.438f, 82.226f, 61.886f, 84.746f, 58f, 86f)
                curveToRelative(-1f, -1f, -1f, -1f, -1.197f, -3.302f)
                curveToRelative(0.215f, -2.938f, 0.834f, -4.374f, 2.35f, -6.87f)
                lineToRelative(1.353f, -2.262f)
                lineToRelative(1.431f, -2.316f)
                curveToRelative(0.47f, -0.78f, 0.938f, -1.56f, 1.42f, -2.363f)
                curveTo(66.785f, 63.215f, 66.785f, 63.215f, 68f, 62f)
            }
        }.build()

        return _HeavyRain!!
    }

@Suppress("ObjectPropertyName")
private var _HeavyRain: ImageVector? = null
