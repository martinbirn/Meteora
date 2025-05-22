@file:Suppress("UnusedReceiverParameter")

package org.meteora.presentation.icon.weather

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val WeatherIcons.ClearNight: ImageVector
    get() {
        if (_ClearNight != null) {
            return _ClearNight!!
        }
        _ClearNight = ImageVector.Builder(
            name = "ClearNight",
            defaultWidth = 96.dp,
            defaultHeight = 90.dp,
            viewportWidth = 96f,
            viewportHeight = 90f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveTo(35f, 19f)
                curveToRelative(1.043f, 2.713f, 1.057f, 3.84f, 0.063f, 6.625f)
                curveToRelative(-2.394f, 7.604f, -1.339f, 14.574f, 1.617f, 21.89f)
                curveToRelative(2.874f, 5.41f, 7.002f, 8.191f, 12.633f, 10.36f)
                curveTo(57.12f, 60.129f, 65.064f, 59.024f, 73f, 58f)
                curveToRelative(-0.27f, 6.463f, -2.507f, 10.37f, -7f, 15f)
                curveToRelative(-8.667f, 6.948f, -18.16f, 7.959f, -29f, 7f)
                curveToRelative(-7.71f, -1.774f, -14.1f, -4.731f, -18.656f, -11.437f)
                curveToRelative(-5.9f, -9.574f, -7.03f, -19.597f, -4.696f, -30.626f)
                curveToRelative(2.658f, -7.268f, 7.42f, -13.375f, 14.04f, -17.437f)
                curveTo(31f, 19f, 31f, 19f, 35f, 19f)
                moveTo(69f, 19f)
                horizontalLineToRelative(4f)
                lineToRelative(2f, 9f)
                lineToRelative(10f, 3f)
                verticalLineToRelative(4f)
                lineToRelative(-1.898f, 0.297f)
                lineToRelative(-2.477f, 0.453f)
                lineToRelative(-2.46f, 0.422f)
                curveToRelative(-2.302f, 0.64f, -2.302f, 0.64f, -3.415f, 2.703f)
                curveToRelative(-0.944f, 2.676f, -1.367f, 5.319f, -1.75f, 8.125f)
                horizontalLineToRelative(-4f)
                lineToRelative(-0.262f, -1.898f)
                curveToRelative(-0.423f, -2.464f, -0.9f, -4.746f, -1.738f, -7.102f)
                curveToRelative(-2.243f, -1.664f, -2.243f, -1.664f, -5f, -2f)
                lineToRelative(-2.812f, -0.625f)
                lineTo(57f, 35f)
                verticalLineToRelative(-4f)
                lineToRelative(10f, -3f)
                close()
                moveTo(53f, 5f)
                horizontalLineToRelative(2f)
                lineToRelative(1.188f, 2.875f)
                curveTo(58.198f, 11.341f, 59.227f, 11.98f, 63f, 13f)
                verticalLineToRelative(2f)
                lineToRelative(-2.875f, 0.625f)
                curveToRelative(-3.417f, 1.083f, -3.417f, 1.083f, -4.5f, 4.5f)
                lineTo(55f, 23f)
                lineToRelative(-3f, -1f)
                lineToRelative(-0.875f, -2.437f)
                curveToRelative(-0.904f, -2.802f, -0.904f, -2.802f, -3.75f, -3.875f)
                lineTo(45f, 15f)
                lineToRelative(1f, -3f)
                lineToRelative(2.438f, -0.875f)
                curveToRelative(2.801f, -0.904f, 2.801f, -0.904f, 3.874f, -3.75f)
                close()
            }
        }.build()

        return _ClearNight!!
    }

@Suppress("ObjectPropertyName")
private var _ClearNight: ImageVector? = null
