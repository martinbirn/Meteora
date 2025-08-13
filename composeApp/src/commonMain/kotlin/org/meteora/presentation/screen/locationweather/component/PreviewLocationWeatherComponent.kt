package org.meteora.presentation.screen.locationweather.component

import dev.icerock.moko.geo.LocationTracker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.meteora.domain.entity.LocationInfo
import org.meteora.presentation.screen.locationweather.LocationWeatherUiState
import org.meteora.presentation.util.preview.WeatherInfoParameters

class PreviewLocationWeatherComponent : LocationWeatherComponent {

    private val sampleWeatherInfo = WeatherInfoParameters().values.first()

    override val weatherState: StateFlow<LocationWeatherUiState> = MutableStateFlow(
        LocationWeatherUiState.Content(weatherInfo = sampleWeatherInfo)
    )

    override val initialLocation: LocationInfo = sampleWeatherInfo.locationInfo

    override fun refresh() {}

    override suspend fun startTracking(locationTracker: LocationTracker) {}

    override fun stopTracking() {}
}