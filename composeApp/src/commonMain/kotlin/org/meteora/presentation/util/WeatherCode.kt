package org.meteora.presentation.util

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.meteora.domain.entity.WeatherCode
import org.meteora.presentation.resources.Res
import org.meteora.presentation.resources._01d
import org.meteora.presentation.resources._01n
import org.meteora.presentation.resources._02d
import org.meteora.presentation.resources._02n
import org.meteora.presentation.resources._03
import org.meteora.presentation.resources._04
import org.meteora.presentation.resources._09
import org.meteora.presentation.resources._10d
import org.meteora.presentation.resources._10n
import org.meteora.presentation.resources._11
import org.meteora.presentation.resources._13
import org.meteora.presentation.resources._50
import org.meteora.presentation.resources.broken_clouds
import org.meteora.presentation.resources.clear
import org.meteora.presentation.resources.clear_sky
import org.meteora.presentation.resources.clouds
import org.meteora.presentation.resources.drizzle
import org.meteora.presentation.resources.few_clouds
import org.meteora.presentation.resources.fog
import org.meteora.presentation.resources.rain
import org.meteora.presentation.resources.rain_showers
import org.meteora.presentation.resources.scattered_clouds
import org.meteora.presentation.resources.snow
import org.meteora.presentation.resources.thunderstorm
import org.meteora.presentation.resources.unknown

val WeatherCode.status: StringResource
    get() = when (this) {
        0 -> Res.string.clear
        1 -> Res.string.clear
        2 -> Res.string.clouds
        3 -> Res.string.clouds
        45, 48 -> Res.string.fog
        51, 53, 55 -> Res.string.drizzle
        61, 63, 65 -> Res.string.rain
        71, 73, 75, 77 -> Res.string.snow
        80, 81, 82 -> Res.string.rain
        95, 96, 99 -> Res.string.thunderstorm
        else -> Res.string.unknown
    }

val WeatherCode.description: StringResource
    get() = when (this) {
        0 -> Res.string.clear_sky
        1 -> Res.string.few_clouds
        2 -> Res.string.scattered_clouds
        3 -> Res.string.broken_clouds
        45, 48 -> Res.string.fog
        51, 53, 55 -> Res.string.drizzle
        61, 63, 65 -> Res.string.rain
        71, 73, 75, 77 -> Res.string.snow
        80, 81, 82 -> Res.string.rain_showers
        95, 96, 99 -> Res.string.thunderstorm
        else -> Res.string.unknown
    }

val WeatherCode.icon: DrawableResource
    @Composable
    get() = if (isSystemInDarkTheme()) nightIcon else dayIcon

val WeatherCode.dayIcon: DrawableResource
    get() = when (this) {
        0 -> Res.drawable._01d
        1 -> Res.drawable._02d
        2 -> Res.drawable._03
        3 -> Res.drawable._04
        45, 48 -> Res.drawable._50
        51, 53, 55 -> Res.drawable._09
        61, 63, 65 -> Res.drawable._10d
        71, 73, 75, 77 -> Res.drawable._13
        80, 81, 82 -> Res.drawable._09
        95, 96, 99 -> Res.drawable._11
        else -> Res.drawable._01d
    }

val WeatherCode.nightIcon: DrawableResource
    get() = when (this) {
        0 -> Res.drawable._01n
        1 -> Res.drawable._02n
        2 -> Res.drawable._03
        3 -> Res.drawable._04
        45, 48 -> Res.drawable._50
        51, 53, 55 -> Res.drawable._09
        61, 63, 65 -> Res.drawable._10n
        71, 73, 75, 77 -> Res.drawable._13
        80, 81, 82 -> Res.drawable._09
        95, 96, 99 -> Res.drawable._11
        else -> Res.drawable._01n
    }