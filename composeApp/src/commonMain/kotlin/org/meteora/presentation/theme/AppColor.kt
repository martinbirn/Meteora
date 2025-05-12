package org.meteora.presentation.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.ui.graphics.Color

object MeteoraColor {
    val Black = Color(0xFF101010)
    val Black30 = Color(0x4D101010)
    val Black50 = Color(0x80101010)
    val Black80 = Color(0xCC101010)
    val Black90 = Color(0xE6101010)
    val BlackSecondary = Color(0xFF171717)
    val BlackButtonBackground = Color(0xFF1D1D1D)
    val Orange = Color(0xFFFF4D14)
    val Gray = Color(0xFFBABABA)
    val LightGray = Color(0xFFEBEBEB)
    val DarkGray = Color(0xFF181818)
    val BorderGray = Color(0xFF2A2A2A)
    val White = Color(0xFFFFFFFF)
    val White5 = Color(0x0DFFFFFF)
    val White20 = Color(0x33FFFFFF)
    val White30 = Color(0x4DFFFFFF)
    val White50 = Color(0x80FFFFFF)
    val Green = Color(0xFF1DED83)
    val Red = Color(0xFFFF4949)
    val PaleCyan = Color(0xFF96FCEF)
    val RssiRed = Color(0xFFFBA1A1)
    val RssiYellow = Color(0xFFFFEEBF)
    val RssiGreen = Color(0xFFA7EFB3)
    val LightShade = Color(0xFF656565)
    val DialogBackground = Black80
    val RadarRed = Color(0xFFFF5656)
    val MissionYellow = Color(0xFFFFE49E)
    val StatusYellow = Color(0xFFFFF614)
    val StatusRed = Color(0xFFFF4949)
    val StatusGreen = Color(0xFF55E050)
    val Olive = Color(0xFF39330C)
    val TemperatureTrackBackground = Color(0x59404040)
    val TemperatureTrackGradientStart = Color(0xFF63D2DC)
    val TemperatureTrackGradientEnd = Color(0xFFC6D289)
    val UvLow = Color(0xFF4CAF50)
    val UvModerate = Color(0xFFFFEB3B)
    val UvHigh = Color(0xFFFF9800)
    val UvVeryHigh = Color(0xFFF44336)
    val UvExtreme = Color(0xFF9C27B0)
}

val MeteoraMaterialDarkColors = darkColorScheme(
    primary = MeteoraColor.White,
    onPrimary = MeteoraColor.Black,
    primaryContainer = MeteoraColor.White50,
    onPrimaryContainer = MeteoraColor.White20,
    secondary = MeteoraColor.White50,
    onSecondary = MeteoraColor.LightShade,
    background = MeteoraColor.White,
    onBackground = MeteoraColor.Black,
    error = MeteoraColor.Red,
    onError = MeteoraColor.White,
    surface = MeteoraColor.Gray,
    onSurface = MeteoraColor.White20,
    surfaceTint = MeteoraColor.Gray,
    surfaceVariant = MeteoraColor.White20,
    onSurfaceVariant = MeteoraColor.White5,
    outline = MeteoraColor.White,
    outlineVariant = MeteoraColor.White5,
    surfaceContainer = MeteoraColor.Gray,
    surfaceContainerLowest = MeteoraColor.Gray,
    surfaceContainerLow = MeteoraColor.Gray,
    surfaceContainerHigh = MeteoraColor.Gray,
    surfaceContainerHighest = MeteoraColor.Gray,
)