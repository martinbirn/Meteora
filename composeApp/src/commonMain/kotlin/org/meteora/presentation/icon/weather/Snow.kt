@file:Suppress("UnusedReceiverParameter")

package org.meteora.presentation.icon.weather

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val WeatherIcons.Snow: ImageVector
    get() {
        if (_Snow != null) {
            return _Snow!!
        }
        _Snow = ImageVector.Builder(
            name = "Snow",
            defaultWidth = 96.dp,
            defaultHeight = 90.dp,
            viewportWidth = 96f,
            viewportHeight = 90f
        ).apply {
            path(fill = SolidColor(Color.White)) {
                moveToRelative(46f, 8f)
                lineToRelative(5f, 1f)
                lineToRelative(1f, 8f)
                lineToRelative(2.25f, -1.562f)
                curveTo(57f, 14f, 57f, 14f, 59.313f, 14.312f)
                lineTo(61f, 15f)
                curveToRelative(-0.062f, 2.25f, -0.062f, 2.25f, -1f, 5f)
                curveToRelative(-2.657f, 2.09f, -5.536f, 4f, -9f, 4f)
                lineToRelative(1f, 15f)
                curveToRelative(5.873f, -3.017f, 5.873f, -3.017f, 11f, -7f)
                curveToRelative(0.947f, -3.052f, 1.074f, -5.463f, 0.973f, -8.645f)
                curveTo(64f, 21f, 64f, 21f, 66f, 19f)
                horizontalLineToRelative(3f)
                curveToRelative(1.478f, 2.957f, 1.06f, 5.742f, 1f, 9f)
                lineToRelative(2.188f, -1.5f)
                curveToRelative(2.96f, -1.58f, 4.525f, -1.782f, 7.812f, -1.5f)
                lineToRelative(1f, 4f)
                curveToRelative(-4.75f, 4f, -4.75f, 4f, -7f, 4f)
                verticalLineToRelative(2f)
                lineToRelative(2.438f, 0.688f)
                lineTo(79f, 37f)
                curveToRelative(0.813f, 2.625f, 0.813f, 2.625f, 1f, 5f)
                curveToRelative(-3.836f, -0.384f, -6.528f, -0.71f, -9.844f, -2.75f)
                curveToRelative(-3.161f, -1.665f, -3.161f, -1.665f, -6.367f, -0.012f)
                lineToRelative(-3.164f, 2.075f)
                lineToRelative(-3.21f, 2.05f)
                lineTo(55f, 45f)
                curveToRelative(7.233f, 4.595f, 7.233f, 4.595f, 15.246f, 5.473f)
                curveTo(71.883f, 49.39f, 73.446f, 48.198f, 75f, 47f)
                curveToRelative(2.938f, 0.25f, 2.938f, 0.25f, 5f, 1f)
                verticalLineToRelative(4f)
                curveToRelative(-2.5f, 2.25f, -2.5f, 2.25f, -5f, 4f)
                lineToRelative(6f, 4f)
                curveToRelative(-0.312f, 1.938f, -0.312f, 1.938f, -1f, 4f)
                curveToRelative(-3f, 1f, -3f, 1f, -6.187f, -0.437f)
                lineTo(71f, 62f)
                lineToRelative(-1f, 7f)
                lineToRelative(-5f, 1f)
                curveToRelative(-1f, -2f, -1f, -2f, -0.93f, -4.355f)
                curveToRelative(0.062f, -3.188f, -0.087f, -5.6f, -1.07f, -8.645f)
                curveToRelative(-3.45f, -3.133f, -6.806f, -4.83f, -11f, -7f)
                lineToRelative(-1f, 15f)
                lineToRelative(5f, 2f)
                curveToRelative(2.813f, 1.313f, 2.813f, 1.313f, 5f, 3f)
                curveToRelative(0.313f, 2.688f, 0.313f, 2.688f, 0f, 5f)
                curveToRelative(-3.916f, -0.534f, -6.393f, -1.197f, -10f, -3f)
                verticalLineToRelative(9f)
                horizontalLineToRelative(-5f)
                lineToRelative(-2f, -8f)
                lineToRelative(-1.75f, 1f)
                curveTo(40f, 75f, 40f, 75f, 36f, 75f)
                curveToRelative(-0.5f, -2.187f, -0.5f, -2.187f, 0f, -5f)
                curveToRelative(1.746f, -1.27f, 1.746f, -1.27f, 3.938f, -2.5f)
                curveToRelative(3.196f, -1.714f, 3.196f, -1.714f, 5.062f, -4.687f)
                verticalLineTo(60f)
                lineToRelative(0.035f, -2.54f)
                arcTo(273f, 273f, 0f, isMoreThanHalf = false, isPositiveArc = false, 45f, 50f)
                curveToRelative(-4.412f, 2.263f, -8.379f, 4.59f, -12f, 8f)
                curveToRelative(-1.302f, 4.091f, -1.369f, 7.744f, -1f, 12f)
                lineToRelative(-5f, -1f)
                lineToRelative(-1f, -7f)
                lineToRelative(-2.75f, 1.625f)
                curveTo(20f, 65f, 20f, 65f, 17.625f, 64.188f)
                lineTo(16f, 63f)
                curveToRelative(0.349f, -3.255f, 1.021f, -4.019f, 3.563f, -6.25f)
                lineTo(22f, 55f)
                lineToRelative(-2.5f, -1.187f)
                lineTo(17f, 52f)
                curveToRelative(-0.312f, -2.687f, -0.312f, -2.687f, 0f, -5f)
                curveToRelative(3.812f, -0.476f, 5.512f, 0.299f, 8.563f, 2.375f)
                curveTo(27.574f, 50.629f, 27.574f, 50.629f, 30f, 51f)
                curveToRelative(4.335f, -1.457f, 8.122f, -3.596f, 12f, -6f)
                curveToRelative(-7.15f, -5.147f, -7.15f, -5.147f, -15.246f, -6.574f)
                curveTo(24.73f, 39.693f, 24.73f, 39.693f, 22f, 42f)
                curveToRelative(-2.937f, 0.25f, -2.937f, 0.25f, -5f, 0f)
                curveToRelative(0.35f, -3.242f, 0.562f, -4.615f, 3.063f, -6.812f)
                lineTo(22f, 34f)
                lineToRelative(-6f, -4f)
                lineToRelative(1f, -5f)
                curveToRelative(4.273f, -0.366f, 6.488f, 0.592f, 10f, 3f)
                verticalLineToRelative(-8f)
                curveToRelative(1.938f, -0.562f, 1.938f, -0.562f, 4f, -1f)
                curveToRelative(1f, 1f, 1f, 1f, 1.098f, 3.723f)
                lineToRelative(-0.035f, 3.34f)
                lineToRelative(-0.028f, 3.347f)
                lineTo(32f, 32f)
                lineToRelative(2.23f, 0.836f)
                curveTo(38.131f, 34.476f, 41.445f, 36.71f, 45f, 39f)
                verticalLineTo(24f)
                curveToRelative(-3.62f, -1.45f, -3.62f, -1.45f, -7.25f, -2.875f)
                curveTo(36f, 20f, 36f, 20f, 35.25f, 17.875f)
                lineTo(35f, 16f)
                curveToRelative(1.625f, -1.187f, 1.625f, -1.187f, 4f, -2f)
                curveToRelative(3.25f, 1.375f, 3.25f, 1.375f, 6f, 3f)
                lineToRelative(-0.062f, -3.375f)
                curveTo(45f, 10f, 45f, 10f, 46f, 8f)
            }
        }.build()

        return _Snow!!
    }

@Suppress("ObjectPropertyName")
private var _Snow: ImageVector? = null
