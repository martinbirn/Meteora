package org.meteora.presentation.screen.locationweather

import org.meteora.domain.entity.LocationInfo
import org.meteora.domain.entity.WeatherInfo

sealed class LocationWeatherUiState {
    data class Init(val locationInfo: LocationInfo) : LocationWeatherUiState()
    object Loading : LocationWeatherUiState()
    data class Content(val weatherInfo: WeatherInfo) : LocationWeatherUiState()
    data class Error(val throwable: Throwable) : LocationWeatherUiState()
}