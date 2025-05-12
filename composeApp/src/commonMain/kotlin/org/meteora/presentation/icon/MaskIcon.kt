package org.meteora.presentation.icon

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val MaskIcon: ImageVector
    get() {
        if (_MaskIcon != null) {
            return _MaskIcon!!
        }
        _MaskIcon = ImageVector.Builder(
            name = "Mask",
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
                moveTo(6.225f, 1.227f)
                arcTo(7.5f, 7.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 10.5f, 8f)
                arcToRelative(
                    7.5f,
                    7.5f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    -4.275f,
                    6.773f
                )
                arcToRelative(
                    7f,
                    7f,
                    0f,
                    isMoreThanHalf = true,
                    isPositiveArc = false,
                    0f,
                    -13.546f
                )
                moveTo(4.187f, 0.966f)
                arcToRelative(
                    8f,
                    8f,
                    0f,
                    isMoreThanHalf = true,
                    isPositiveArc = true,
                    7.627f,
                    14.069f
                )
                arcTo(8f, 8f, 0f, isMoreThanHalf = false, isPositiveArc = true, 4.186f, 0.964f)
                close()
            }
        }.build()
        return _MaskIcon!!
    }

private var _MaskIcon: ImageVector? = null
