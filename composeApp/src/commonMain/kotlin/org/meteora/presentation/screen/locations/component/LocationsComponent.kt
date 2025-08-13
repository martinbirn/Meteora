package org.meteora.presentation.screen.locations.component

import kotlinx.coroutines.flow.StateFlow
import org.meteora.domain.entity.LocationInfo
import org.meteora.presentation.screen.locations.LocationsUiState

interface LocationsComponent {
    val uiState: StateFlow<LocationsUiState>

    fun getLocationInfoByKey(key: String): LocationInfo?

    fun navigateToLocationSearch()
    fun navigateToLocationWeather(locationInfo: LocationInfo)
}
