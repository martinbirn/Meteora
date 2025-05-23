package org.meteora.presentation.util

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.StringResource
import org.meteora.domain.entity.WeatherCode
import org.meteora.presentation.icon.weather.Clear
import org.meteora.presentation.icon.weather.ClearNight
import org.meteora.presentation.icon.weather.Cloudy
import org.meteora.presentation.icon.weather.Drizzle
import org.meteora.presentation.icon.weather.DrizzleNight
import org.meteora.presentation.icon.weather.Fog
import org.meteora.presentation.icon.weather.FreezingRain
import org.meteora.presentation.icon.weather.HeavyRain
import org.meteora.presentation.icon.weather.HeavySnow
import org.meteora.presentation.icon.weather.PartlyCloudy
import org.meteora.presentation.icon.weather.PartlyCloudyNight
import org.meteora.presentation.icon.weather.Rain
import org.meteora.presentation.icon.weather.Snow
import org.meteora.presentation.icon.weather.Thunderstorm
import org.meteora.presentation.icon.weather.WeatherIcons
import org.meteora.presentation.resources.Res
import org.meteora.presentation.resources.broken_clouds
import org.meteora.presentation.resources.clear
import org.meteora.presentation.resources.clear_sky
import org.meteora.presentation.resources.clouds
import org.meteora.presentation.resources.drizzle
import org.meteora.presentation.resources.few_clouds
import org.meteora.presentation.resources.fog
import org.meteora.presentation.resources.freezing_drizzle
import org.meteora.presentation.resources.freezing_rain
import org.meteora.presentation.resources.rain
import org.meteora.presentation.resources.rain_showers
import org.meteora.presentation.resources.scattered_clouds
import org.meteora.presentation.resources.snow
import org.meteora.presentation.resources.snow_grains
import org.meteora.presentation.resources.snow_showers
import org.meteora.presentation.resources.thunderstorm
import org.meteora.presentation.resources.thunderstorm_with_hail
import org.meteora.presentation.resources.unknown

val Int.status: StringResource
    get() = when (this) {
        0 -> Res.string.clear
        1 -> Res.string.few_clouds
        2, 3 -> Res.string.clouds
        45, 48 -> Res.string.fog
        51, 53, 55 -> Res.string.drizzle
        56, 57 -> Res.string.freezing_drizzle
        61, 63, 65 -> Res.string.rain
        66, 67 -> Res.string.freezing_rain
        71, 73, 75 -> Res.string.snow
        77 -> Res.string.snow
        80, 81, 82 -> Res.string.rain
        85, 86 -> Res.string.snow
        95, 96, 99 -> Res.string.thunderstorm
        else -> Res.string.unknown
    }

val Int.description: StringResource
    get() = when (this) {
        0 -> Res.string.clear_sky
        1 -> Res.string.few_clouds
        2 -> Res.string.scattered_clouds
        3 -> Res.string.broken_clouds
        45, 48 -> Res.string.fog
        51, 53, 55 -> Res.string.drizzle
        56, 57 -> Res.string.freezing_drizzle
        61, 63, 65 -> Res.string.rain
        66, 67 -> Res.string.freezing_rain
        71, 73, 75 -> Res.string.snow
        77 -> Res.string.snow_grains
        80, 81, 82 -> Res.string.rain_showers
        85, 86 -> Res.string.snow_showers
        95 -> Res.string.thunderstorm
        96, 99 -> Res.string.thunderstorm_with_hail
        else -> Res.string.unknown
    }

val WeatherCode.icon: ImageVector
    @Composable
    get() = if (isSystemInDarkTheme()) nightIcon else dayIcon

val Int.dayIcon: ImageVector
    get() = when (this) {
        0 -> WeatherIcons.Clear
        1 -> WeatherIcons.PartlyCloudy
        2 -> WeatherIcons.Cloudy
        3 -> WeatherIcons.Cloudy
        45, 48 -> WeatherIcons.Fog
        51, 53, 55 -> WeatherIcons.Drizzle
        56, 57 -> WeatherIcons.FreezingRain
        61, 63, 65 -> WeatherIcons.Rain
        66, 67 -> WeatherIcons.FreezingRain
        71, 73 -> WeatherIcons.Snow
        75, 77 -> WeatherIcons.HeavySnow
        80, 81 -> WeatherIcons.HeavyRain
        82 -> WeatherIcons.Thunderstorm
        85 -> WeatherIcons.Snow
        86 -> WeatherIcons.HeavySnow
        95 -> WeatherIcons.Thunderstorm
        96, 99 -> WeatherIcons.Thunderstorm
        else -> WeatherIcons.Clear
    }

val Int.nightIcon: ImageVector
    get() = when (this) {
        0 -> WeatherIcons.ClearNight
        1 -> WeatherIcons.PartlyCloudyNight
        2, 3 -> WeatherIcons.Cloudy
        45, 48 -> WeatherIcons.Fog
        51, 53, 55 -> WeatherIcons.DrizzleNight
        56, 57 -> WeatherIcons.FreezingRain
        61, 63, 65 -> WeatherIcons.Rain
        66, 67 -> WeatherIcons.FreezingRain
        71, 73 -> WeatherIcons.Snow
        75, 77 -> WeatherIcons.HeavySnow
        80, 81 -> WeatherIcons.HeavyRain
        82 -> WeatherIcons.Thunderstorm
        85 -> WeatherIcons.Snow
        86 -> WeatherIcons.HeavySnow
        95 -> WeatherIcons.Thunderstorm
        96, 99 -> WeatherIcons.Thunderstorm
        else -> WeatherIcons.ClearNight
    }