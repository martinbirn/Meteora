@file:Suppress("UnusedReceiverParameter")

package org.meteora.presentation.icon.weather

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val WeatherIcons.Rain: ImageVector
    get() {
        if (_Rain != null) {
            return _Rain!!
        }
        _Rain = ImageVector.Builder(
            name = "Rain",
            defaultWidth = 96.dp,
            defaultHeight = 90.dp,
            viewportWidth = 96f,
            viewportHeight = 90f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(59f, 6f)
                curveToRelative(3.636f, 2.457f, 6.4f, 4.799f, 8.375f, 8.75f)
                curveToRelative(2.299f, 3.183f, 4.61f, 3.078f, 8.348f, 3.738f)
                curveTo(81.792f, 19.852f, 85.456f, 24.24f, 89f, 29f)
                curveToRelative(1.96f, 5.88f, 1.828f, 12.603f, -0.187f, 18.438f)
                curveTo(85.27f, 52.446f, 81.768f, 55.822f, 76f, 58f)
                curveToRelative(-4.426f, 0.457f, -8.835f, 0.444f, -13.281f, 0.434f)
                lineToRelative(-3.9f, 0.015f)
                quadToRelative(-4.074f, 0.013f, -8.15f, -0.003f)
                curveToRelative(-3.47f, -0.011f, -6.939f, 0.009f, -10.41f, 0.038f)
                curveToRelative(-3.325f, 0.023f, -6.651f, 0.016f, -9.978f, 0.012f)
                lineToRelative(-3.76f, 0.034f)
                curveTo(15.834f, 58.444f, 15.834f, 58.444f, 11f, 54f)
                curveToRelative(-3.228f, -3.86f, -4.34f, -8.013f, -4.187f, -13f)
                curveToRelative(0.83f, -5.66f, 3.866f, -9.387f, 8.187f, -13f)
                lineToRelative(3f, -1f)
                curveToRelative(0.998f, -2.293f, 0.998f, -2.293f, 1.75f, -5.062f)
                curveToRelative(2.465f, -7.218f, 6.357f, -12.422f, 12.938f, -16.375f)
                curveTo(41.148f, 1.572f, 50.606f, 1.826f, 59f, 6f)
            }
            path(fill = SolidColor(Color(0xFF36C8EB))) {
                moveToRelative(69f, 63f)
                lineToRelative(5f, 1f)
                curveToRelative(-0.527f, 5.269f, -3.224f, 9.297f, -5.875f, 13.75f)
                lineToRelative(-1.355f, 2.363f)
                lineToRelative(-1.325f, 2.246f)
                lineToRelative(-1.202f, 2.046f)
                curveTo(63f, 86f, 63f, 86f, 60f, 87f)
                lineToRelative(-2f, -1f)
                curveToRelative(0.443f, -5.896f, 3.037f, -9.965f, 6.063f, -14.875f)
                lineToRelative(1.431f, -2.375f)
                arcTo(772f, 772f, 0f, isMoreThanHalf = false, isPositiveArc = true, 69f, 63f)
                moveTo(41f, 63f)
                horizontalLineToRelative(4f)
                curveToRelative(-0.446f, 5.935f, -2.23f, 9.811f, -5.375f, 14.813f)
                curveToRelative(-0.4f, 0.667f, -0.8f, 1.334f, -1.21f, 2.021f)
                curveTo(36.491f, 82.967f, 35.094f, 84.937f, 32f, 87f)
                lineToRelative(-2f, -1f)
                curveToRelative(0.436f, -5.996f, 2.292f, -9.849f, 5.5f, -14.875f)
                lineToRelative(1.262f, -2.047f)
                curveTo(39.77f, 64.23f, 39.77f, 64.23f, 41f, 63f)
                moveTo(26f, 63f)
                horizontalLineToRelative(5f)
                curveToRelative(0.839f, 5.367f, -2.08f, 8.796f, -5f, 13f)
                lineToRelative(-5f, -1f)
                curveToRelative(0.576f, -4.755f, 2.55f, -7.97f, 5f, -12f)
                moveTo(55f, 63f)
                lineToRelative(5f, 1f)
                curveToRelative(-0.638f, 3.112f, -1.683f, 5.526f, -3.312f, 8.25f)
                curveToRelative(-0.41f, 0.706f, -0.818f, 1.413f, -1.239f, 2.14f)
                lineTo(54f, 76f)
                horizontalLineToRelative(-4f)
                curveToRelative(0.45f, -5.625f, 1.684f, -8.615f, 5f, -13f)
            }
        }.build()

        return _Rain!!
    }

@Suppress("ObjectPropertyName")
private var _Rain: ImageVector? = null
