package org.meteora.presentation.screen.locations.component

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.meteora.domain.entity.LocationInfo
import org.meteora.domain.entity.WeatherInfo
import org.meteora.presentation.screen.locations.LocationsUiState

class PreviewLocationsComponent(private val weathers: Sequence<WeatherInfo>) : LocationsComponent {

    override val uiState: StateFlow<LocationsUiState> = MutableStateFlow(
        value = LocationsUiState.Content(
            weathers = weathers.map {
                it.toShortInfo(key = it.hashCode().toString())
            }.toList()
        )
    )

    override fun navigateToLocationSearch() {}

    override fun navigateToLocationWeather(locationInfo: LocationInfo) {}

    override fun getLocationInfoByKey(key: String): LocationInfo? = null
}
