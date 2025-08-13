package org.meteora.presentation.screen.locationweathersheet.component

import kotlinx.coroutines.flow.StateFlow
import org.meteora.domain.entity.LocationInfo
import org.meteora.presentation.decompose.bottomsheet.BottomSheetContentComponent
import org.meteora.presentation.screen.locationweather.component.LocationWeatherComponent
import org.meteora.presentation.screen.locationweathersheet.LocationWeatherSheetUiState

interface LocationWeatherSheetComponent : BottomSheetContentComponent {
    val state: StateFlow<LocationWeatherSheetUiState>
    val weatherComponent: LocationWeatherComponent
    val locationInfo: LocationInfo

    fun navigateBack()

    // Add the selected location to the local repository
    fun addLocation(locationInfo: LocationInfo)
}