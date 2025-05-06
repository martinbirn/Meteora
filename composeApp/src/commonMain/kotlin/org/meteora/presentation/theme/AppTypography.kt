package org.meteora.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.Font
import org.meteora.presentation.resources.Res
import org.meteora.presentation.resources.SF_Pro_Text_Medium
import org.meteora.presentation.resources.SF_Pro_Text_Regular

@Composable
fun MeteoraFontFamily() = FontFamily(
    Font(Res.font.SF_Pro_Text_Regular, weight = FontWeight.Normal),
    Font(Res.font.SF_Pro_Text_Medium, weight = FontWeight.Medium)
)

@Composable
fun MeteoraTypography() = Typography().run {
    val fontFamily = MeteoraFontFamily()
    copy(
        displayLarge = displayLarge.copy(fontFamily = fontFamily, color = Color.White),
        displayMedium = displayMedium.copy(fontFamily = fontFamily, color = Color.White),
        displaySmall = displaySmall.copy(fontFamily = fontFamily, color = Color.White),
        headlineLarge = headlineLarge.copy(fontFamily = fontFamily, color = Color.White),
        headlineMedium = headlineMedium.copy(fontFamily = fontFamily, color = Color.White),
        headlineSmall = headlineSmall.copy(fontFamily = fontFamily, color = Color.White),
        titleLarge = titleLarge.copy(fontFamily = fontFamily, color = Color.White),
        titleMedium = titleMedium.copy(fontFamily = fontFamily, color = Color.White),
        titleSmall = titleSmall.copy(fontFamily = fontFamily, color = Color.White),
        bodyLarge = bodyLarge.copy(fontFamily = fontFamily, color = Color.White),
        bodyMedium = bodyMedium.copy(fontFamily = fontFamily, color = Color.White),
        bodySmall = bodySmall.copy(fontFamily = fontFamily, color = Color.White),
        labelLarge = labelLarge.copy(fontFamily = fontFamily, color = Color.White),
        labelMedium = labelMedium.copy(fontFamily = fontFamily, color = Color.White),
        labelSmall = labelSmall.copy(fontFamily = fontFamily, color = Color.White),
    )
}