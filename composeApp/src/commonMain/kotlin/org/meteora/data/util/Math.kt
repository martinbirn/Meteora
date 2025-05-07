package org.meteora.data.util

import io.github.aakira.napier.Napier
import kotlin.math.PI

fun Double.toRadians(): Double = this * PI / 180.0
fun Float.toRadians(): Float = (this * PI / 180.0).toFloat()

fun calculateSunProgress(
    currentTimeSeconds: Long,
    sunriseSeconds: Long,
    sunsetSeconds: Long
): Float {
    Napier.d(
        message = "currentTimeSeconds: $currentTimeSeconds, sunriseSeconds: $sunriseSeconds, sunsetSeconds: $sunsetSeconds",
        tag = "WFT"
    )
    val dayDuration = sunsetSeconds - sunriseSeconds
    if (dayDuration <= 0) return -1f

    val result = ((currentTimeSeconds - sunriseSeconds).toFloat() / dayDuration).coerceIn(-1f, 2f)
    Napier.d(message = "result: $result", tag = "WFT")
    return result
}