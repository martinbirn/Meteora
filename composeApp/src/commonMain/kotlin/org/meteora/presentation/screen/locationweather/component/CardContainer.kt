package org.meteora.presentation.screen.locationweather.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.chrisbanes.haze.hazeEffect
import dev.chrisbanes.haze.materials.HazeMaterials
import org.meteora.presentation.theme.MeteoraColor
import org.meteora.presentation.util.LocalHazeState

@Composable
fun SquareContainer(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    BlurredContainer(
        modifier = modifier.aspectRatio(ratio = 1f),
        content = content
    )
}

@Composable
fun BlurredContainer(
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier = modifier
            .background(color = MeteoraColor.Black30, shape = RoundedCornerShape(size = 12.dp))
            .padding(all = 12.dp)
            .hazeEffect(
                state = LocalHazeState.current,
                style = HazeMaterials.ultraThin()
            ),
        content = content
    )
}