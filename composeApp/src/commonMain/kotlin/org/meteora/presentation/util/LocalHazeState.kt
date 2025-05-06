package org.meteora.presentation.util

import androidx.compose.runtime.compositionLocalOf
import dev.chrisbanes.haze.HazeState

val LocalHazeState = compositionLocalOf { HazeState() }