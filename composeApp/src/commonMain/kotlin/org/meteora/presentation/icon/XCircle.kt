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

val MeteoraIcons.XCircle: ImageVector
    get() {
        if (_XCircle != null) {
            return _XCircle!!
        }
        _XCircle = ImageVector.Builder(
            name = "XCircle",
            defaultWidth = 16.dp,
            defaultHeight = 16.dp,
            viewportWidth = 16f,
            viewportHeight = 16f
        ).apply {
            path(
                fill = SolidColor(Color.White),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(8f, 15f)
                arcTo(7f, 7f, 0f, isMoreThanHalf = true, isPositiveArc = true, 8f, 1f)
                arcToRelative(7f, 7f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0f, 14f)
                moveToRelative(0f, 1f)
                arcTo(8f, 8f, 0f, isMoreThanHalf = true, isPositiveArc = false, 8f, 0f)
                arcToRelative(8f, 8f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0f, 16f)
            }
            path(
                fill = SolidColor(Color.White),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(4.646f, 4.646f)
                arcToRelative(
                    0.5f,
                    0.5f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    0.708f,
                    0f
                )
                lineTo(8f, 7.293f)
                lineToRelative(2.646f, -2.647f)
                arcToRelative(
                    0.5f,
                    0.5f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    0.708f,
                    0.708f
                )
                lineTo(8.707f, 8f)
                lineToRelative(2.647f, 2.646f)
                arcToRelative(
                    0.5f,
                    0.5f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    -0.708f,
                    0.708f
                )
                lineTo(8f, 8.707f)
                lineToRelative(-2.646f, 2.647f)
                arcToRelative(
                    0.5f,
                    0.5f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    -0.708f,
                    -0.708f
                )
                lineTo(7.293f, 8f)
                lineTo(4.646f, 5.354f)
                arcToRelative(
                    0.5f,
                    0.5f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    0f,
                    -0.708f
                )
            }
        }.build()
        return _XCircle!!
    }

@Suppress("ObjectPropertyName")
private var _XCircle: ImageVector? = null
