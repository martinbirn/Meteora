package org.meteora.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import org.meteora.presentation.resources.Res
import org.meteora.presentation.resources.SF_Pro_Text_Medium
import org.meteora.presentation.resources.SF_Pro_Text_Regular


val meteoraFontFamily: FontFamily
    @Composable
    get() = FontFamily(
        Font(Res.font.SF_Pro_Text_Regular, weight = FontWeight.Normal),
        Font(Res.font.SF_Pro_Text_Medium, weight = FontWeight.Medium)
    )

val meteoraTypography: Typography
    @Composable
    get() = Typography().run {
        val fontFamily = meteoraFontFamily
        copy(
            displayLarge = displayLarge.copy(fontFamily = fontFamily, color = MeteoraColor.White),
            displayMedium = displayMedium.copy(fontFamily = fontFamily, color = MeteoraColor.White),
            displaySmall = displaySmall.copy(fontFamily = fontFamily, color = MeteoraColor.White),
            headlineLarge = headlineLarge.copy(fontFamily = fontFamily, color = MeteoraColor.White),
            headlineMedium = headlineMedium.copy(fontFamily = fontFamily, color = MeteoraColor.White),
            headlineSmall = headlineSmall.copy(fontFamily = fontFamily, color = MeteoraColor.White),
            titleLarge = titleLarge.copy(fontFamily = fontFamily, color = MeteoraColor.White),
            titleMedium = titleMedium.copy(fontFamily = fontFamily, color = MeteoraColor.White),
            titleSmall = titleSmall.copy(fontFamily = fontFamily, color = MeteoraColor.White),
            bodyLarge = bodyLarge.copy(fontFamily = fontFamily, color = MeteoraColor.White),
            bodyMedium = bodyMedium.copy(fontFamily = fontFamily, color = MeteoraColor.White),
            bodySmall = bodySmall.copy(fontFamily = fontFamily, color = MeteoraColor.White),
            labelLarge = labelLarge.copy(fontFamily = fontFamily, color = MeteoraColor.White),
            labelMedium = labelMedium.copy(fontFamily = fontFamily, color = MeteoraColor.White),
            labelSmall = labelSmall.copy(fontFamily = fontFamily, color = MeteoraColor.White),
        )
    }
