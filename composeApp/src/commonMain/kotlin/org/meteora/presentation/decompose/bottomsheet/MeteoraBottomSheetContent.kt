package org.meteora.presentation.decompose.bottomsheet

import androidx.compose.runtime.Composable
import org.meteora.presentation.decompose.LocationWeatherSheetComponent
import org.meteora.presentation.screen.locationweather.LocationWeatherSheet

@Composable
fun MeteoraBottomSheetContent(component: BottomSheetContentComponent) {
    when (component) {
        is LocationWeatherSheetComponent -> {
            LocationWeatherSheet(component = component)
        }
    }
}
