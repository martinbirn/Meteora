package org.meteora.presentation.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

val LocalDimensions = staticCompositionLocalOf { Dimensions() }

object MeteoraTheme {
    val dimen: Dimensions
        @Composable
        @ReadOnlyComposable
        get() = LocalDimensions.current
}

@Immutable
data class Dimensions(
    val iconSize: DpSize = DpSize(24.dp, 24.dp),
    val horizontalPadding: Dp = 20.dp,
    val verticalPadding: Dp = 20.dp,
    val toolbarHeight: Dp = 56.dp,
    val toolbarPadding: Dp = 18.dp
)

@Composable
fun MeteoraTheme(
    dimensions: Dimensions = Dimensions(),
    colors: ColorScheme = MeteoraMaterialDarkColors,
    typography: Typography = MeteoraTypography(),
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(LocalDimensions provides dimensions) {
        MaterialTheme(
            content = content,
            colorScheme = colors,
            typography = typography
        )
    }
}