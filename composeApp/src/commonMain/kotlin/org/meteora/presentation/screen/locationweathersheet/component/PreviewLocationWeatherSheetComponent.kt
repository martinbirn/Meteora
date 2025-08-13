package org.meteora.presentation.screen.locationweathersheet.component

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.meteora.domain.entity.LocationInfo
import org.meteora.presentation.decompose.bottomsheet.BottomSheetContentState
import org.meteora.presentation.screen.locationweather.component.PreviewLocationWeatherComponent
import org.meteora.presentation.screen.locationweathersheet.LocationWeatherSheetUiState
import org.meteora.presentation.util.preview.WeatherInfoParameters

class PreviewLocationWeatherSheetComponent : LocationWeatherSheetComponent {
    private val sampleWeatherInfo = WeatherInfoParameters().values.first()
    
    override val state: StateFlow<LocationWeatherSheetUiState> = MutableStateFlow(
        LocationWeatherSheetUiState(isDismissAllowed = true)
    )

    override val bottomSheetContentState: StateFlow<BottomSheetContentState> = state

    override val weatherComponent = PreviewLocationWeatherComponent()

    override val locationInfo: LocationInfo = sampleWeatherInfo.locationInfo

    override fun navigateBack() {}

    override fun addLocation(locationInfo: LocationInfo) {}
}