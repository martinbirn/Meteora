package org.meteora.presentation.util

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import kotlin.math.roundToInt

@Composable
fun directionName(degrees: Int): String {
    val directions = remember {
        listOf(
            "N", "NNE", "NE", "ENE",
            "E", "ESE", "SE", "SSE",
            "S", "SSW", "SW", "WSW",
            "W", "WNW", "NW", "NNW"
        )
    }
    val index = ((degrees % 360) / 22.5).roundToInt() % 16
    return directions[index]
}
