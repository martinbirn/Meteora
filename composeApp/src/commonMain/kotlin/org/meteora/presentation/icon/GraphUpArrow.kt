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

val MeteoraIcons.GraphUpArrow: ImageVector
    get() {
        if (_GraphUpArrow != null) {
            return _GraphUpArrow!!
        }
        _GraphUpArrow = ImageVector.Builder(
            name = "GraphUpArrow",
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
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(0f, 0f)
                horizontalLineToRelative(1f)
                verticalLineToRelative(15f)
                horizontalLineToRelative(15f)
                verticalLineToRelative(1f)
                horizontalLineTo(0f)
                close()
                moveToRelative(10f, 3.5f)
                arcToRelative(
                    0.5f,
                    0.5f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    0.5f,
                    -0.5f
                )
                horizontalLineToRelative(4f)
                arcToRelative(
                    0.5f,
                    0.5f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    0.5f,
                    0.5f
                )
                verticalLineToRelative(4f)
                arcToRelative(0.5f, 0.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1f, 0f)
                verticalLineTo(4.9f)
                lineToRelative(-3.613f, 4.417f)
                arcToRelative(
                    0.5f,
                    0.5f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    -0.74f,
                    0.037f
                )
                lineTo(7.06f, 6.767f)
                lineToRelative(-3.656f, 5.027f)
                arcToRelative(
                    0.5f,
                    0.5f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    -0.808f,
                    -0.588f
                )
                lineToRelative(4f, -5.5f)
                arcToRelative(
                    0.5f,
                    0.5f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    0.758f,
                    -0.06f
                )
                lineToRelative(2.609f, 2.61f)
                lineTo(13.445f, 4f)
                horizontalLineTo(10.5f)
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
        return _GraphUpArrow!!
    }

@Suppress("ObjectPropertyName")
private var _GraphUpArrow: ImageVector? = null
