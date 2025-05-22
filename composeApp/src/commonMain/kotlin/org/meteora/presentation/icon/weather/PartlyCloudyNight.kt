@file:Suppress("UnusedReceiverParameter")

package org.meteora.presentation.icon.weather

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val WeatherIcons.PartlyCloudyNight: ImageVector
    get() {
        if (_PartlyCloudyNight != null) {
            return _PartlyCloudyNight!!
        }
        _PartlyCloudyNight = ImageVector.Builder(
            name = "PartlyCloudyNight",
            defaultWidth = 96.dp,
            defaultHeight = 90.dp,
            viewportWidth = 96f,
            viewportHeight = 90f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(44.438f, 24.563f)
                curveToRelative(2.79f, 1.624f, 4.486f, 3.448f, 6.437f, 6f)
                curveToRelative(3.019f, 3.882f, 6.002f, 4.493f, 10.652f, 5.636f)
                curveTo(65.46f, 37.473f, 68.407f, 39.804f, 71f, 43f)
                curveToRelative(2.584f, 5.237f, 2.666f, 10.27f, 2f, 16f)
                curveToRelative(-1.656f, 4.88f, -4.743f, 8.162f, -9f, 11f)
                curveToRelative(-2.937f, 0.979f, -4.937f, 1.154f, -8.003f, 1.209f)
                lineToRelative(-3.086f, 0.058f)
                curveToRelative(-4.656f, 0.058f, -9.313f, 0.107f, -13.97f, 0.138f)
                quadToRelative(-3.68f, 0.033f, -7.36f, 0.103f)
                arcToRelative(
                    785f,
                    785f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    -10.628f,
                    0.113f
                )
                lineToRelative(-3.309f, 0.084f)
                curveToRelative(-6.307f, -0.017f, -9.895f, -0.697f, -14.435f, -5.219f)
                curveTo(0.282f, 62.888f, 0.676f, 59.238f, 0.71f, 54.77f)
                curveTo(1.108f, 50.965f, 2.469f, 48.82f, 5f, 46f)
                curveToRelative(3f, -2f, 3f, -2f, 6f, -2f)
                lineToRelative(0.125f, -3.5f)
                curveToRelative(0.809f, -6.177f, 4.114f, -10.206f, 8.816f, -14.113f)
                curveToRelative(7.226f, -4.724f, 16.568f, -5.318f, 24.497f, -1.825f)
            }
            path(fill = SolidColor(Color.White)) {
                moveTo(72f, 10f)
                curveToRelative(0.125f, 5.75f, 0.125f, 5.75f, -1f, 8f)
                curveToRelative(-0.344f, 5.253f, -0.577f, 8.826f, 3f, 13f)
                curveToRelative(6.727f, 4.434f, 13.445f, 3.06f, 21f, 2f)
                curveToRelative(0.46f, 5.746f, -0.325f, 8.551f, -4f, 13f)
                curveToRelative(-4.55f, 4.512f, -8.518f, 7f, -15f, 7f)
                lineToRelative(-0.687f, -3.562f)
                curveToRelative(-1.36f, -5.297f, -3.554f, -9.612f, -8.208f, -12.692f)
                curveToRelative(-3.086f, -1.6f, -6.086f, -2.74f, -9.418f, -3.746f)
                curveTo(55f, 32f, 55f, 32f, 52.938f, 30.438f)
                curveToRelative(-1.362f, -3.542f, -0.29f, -5.805f, 1.051f, -9.258f)
                curveToRelative(3.455f, -7.444f, 9.6f, -12f, 18.012f, -11.18f)
            }
        }.build()

        return _PartlyCloudyNight!!
    }

@Suppress("ObjectPropertyName")
private var _PartlyCloudyNight: ImageVector? = null
