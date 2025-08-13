package org.meteora.presentation.screen.locationweather.component

import dev.icerock.moko.geo.LocationTracker
import kotlinx.coroutines.flow.StateFlow
import org.meteora.domain.entity.LocationInfo
import org.meteora.presentation.screen.locationweather.LocationWeatherUiState

interface LocationWeatherComponent {
    val weatherState: StateFlow<LocationWeatherUiState>

    val initialLocation: LocationInfo?

    // Trigger a manual refresh of weather data
    fun refresh()

    // Start tracking user location (if initial location not provided)
    suspend fun startTracking(locationTracker: LocationTracker)

    // Stop location tracking when leaving the screen
    fun stopTracking()
}