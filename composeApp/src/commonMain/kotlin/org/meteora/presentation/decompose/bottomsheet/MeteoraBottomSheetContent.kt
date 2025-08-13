package org.meteora.presentation.decompose.bottomsheet

import androidx.compose.runtime.Composable
import org.meteora.presentation.screen.locationweathersheet.LocationWeatherSheet
import org.meteora.presentation.screen.locationweathersheet.component.LocationWeatherSheetComponent

@Composable
fun MeteoraBottomSheetContent(component: BottomSheetContentComponent) {
    when (component) {
        is LocationWeatherSheetComponent -> {
            LocationWeatherSheet(component = component)
        }
    }
}
