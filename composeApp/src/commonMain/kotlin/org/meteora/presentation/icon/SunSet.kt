@file:Suppress("UnusedReceiverParameter")

package org.meteora.presentation.icon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val MeteoraIcons.SunSet: ImageVector
    get() {
        if (_SunSet != null) {
            return _SunSet!!
        }
        _SunSet = ImageVector.Builder(
            name = "Sunset",
            defaultWidth = 16.dp,
            defaultHeight = 16.dp,
            viewportWidth = 16f,
            viewportHeight = 16f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(7.646f, 4.854f)
                arcToRelative(
                    0.5f,
                    0.5f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    0.708f,
                    0f
                )
                lineToRelative(1.5f, -1.5f)
                arcToRelative(
                    0.5f,
                    0.5f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -0.708f,
                    -0.708f
                )
                lineToRelative(-0.646f, 0.647f)
                verticalLineTo(1.5f)
                arcToRelative(
                    0.5f,
                    0.5f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -1f,
                    0f
                )
                verticalLineToRelative(1.793f)
                lineToRelative(-0.646f, -0.647f)
                arcToRelative(
                    0.5f,
                    0.5f,
                    0f,
                    isMoreThanHalf = true,
                    isPositiveArc = false,
                    -0.708f,
                    0.708f
                )
                close()
                moveToRelative(-5.303f, -0.51f)
                arcToRelative(
                    0.5f,
                    0.5f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    0.707f,
                    0f
                )
                lineToRelative(1.414f, 1.413f)
                arcToRelative(
                    0.5f,
                    0.5f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    -0.707f,
                    0.707f
                )
                lineTo(2.343f, 5.05f)
                arcToRelative(
                    0.5f,
                    0.5f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    0f,
                    -0.707f
                )
                close()
                moveToRelative(11.314f, 0f)
                arcToRelative(
                    0.5f,
                    0.5f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    0f,
                    0.706f
                )
                lineToRelative(-1.414f, 1.414f)
                arcToRelative(
                    0.5f,
                    0.5f,
                    0f,
                    isMoreThanHalf = true,
                    isPositiveArc = true,
                    -0.707f,
                    -0.707f
                )
                lineToRelative(1.414f, -1.414f)
                arcToRelative(
                    0.5f,
                    0.5f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    0.707f,
                    0f
                )
                close()
                moveTo(8f, 7f)
                arcToRelative(
                    3f,
                    3f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    2.599f,
                    4.5f
                )
                horizontalLineTo(5.4f)
                arcTo(3f, 3f, 0f, isMoreThanHalf = false, isPositiveArc = true, 8f, 7f)
                moveToRelative(3.71f, 4.5f)
                arcToRelative(4f, 4f, 0f, isMoreThanHalf = true, isPositiveArc = false, -7.418f, 0f)
                horizontalLineTo(0.499f)
                arcToRelative(0.5f, 0.5f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0f, 1f)
                horizontalLineToRelative(15f)
                arcToRelative(
                    0.5f,
                    0.5f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    0f,
                    -1f
                )
                horizontalLineToRelative(-3.79f)
                close()
                moveTo(0f, 10f)
                arcToRelative(
                    0.5f,
                    0.5f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    0.5f,
                    -0.5f
                )
                horizontalLineToRelative(2f)
                arcToRelative(0.5f, 0.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0f, 1f)
                horizontalLineToRelative(-2f)
                arcTo(0.5f, 0.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0f, 10f)
                moveToRelative(13f, 0f)
                arcToRelative(
                    0.5f,
                    0.5f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    0.5f,
                    -0.5f
                )
                horizontalLineToRelative(2f)
                arcToRelative(0.5f, 0.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0f, 1f)
                horizontalLineToRelative(-2f)
                arcToRelative(
                    0.5f,
                    0.5f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    -0.5f,
                    -0.5f
                )
            }
        }.build()
        return _SunSet!!
    }

@Suppress("ObjectPropertyName")
private var _SunSet: ImageVector? = null
