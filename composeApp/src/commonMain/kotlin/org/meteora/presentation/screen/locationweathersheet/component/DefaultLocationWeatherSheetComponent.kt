package org.meteora.presentation.screen.locationweathersheet.component

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.meteora.domain.entity.LocationInfo
import org.meteora.domain.repository.WeatherApiRepository
import org.meteora.domain.repository.WeatherLocalRepository
import org.meteora.presentation.decompose.bottomsheet.BottomSheetContentState
import org.meteora.presentation.screen.locationweather.component.DefaultLocationWeatherComponent
import org.meteora.presentation.screen.locationweathersheet.LocationWeatherSheetUiState

class DefaultLocationWeatherSheetComponent(
    componentContext: ComponentContext,
    override val locationInfo: LocationInfo,
    weatherApiRepository: WeatherApiRepository,
    private val weatherLocalRepository: WeatherLocalRepository,
    private val onNavigateBack: () -> Unit,
    private val onAddLocation: () -> Unit,
) : LocationWeatherSheetComponent, ComponentContext by componentContext {

    private val _state = MutableStateFlow(
        LocationWeatherSheetUiState(isDismissAllowed = true)
    )

    override val state: StateFlow<LocationWeatherSheetUiState> = _state

    override val bottomSheetContentState: StateFlow<BottomSheetContentState> = state

    override val weatherComponent =
        DefaultLocationWeatherComponent(
            componentContext = componentContext,
            initialLocation = locationInfo,
            weatherApiRepository = weatherApiRepository
        )

    override fun navigateBack() {
        onNavigateBack()
    }

    override fun addLocation(locationInfo: LocationInfo) {
        weatherLocalRepository.addLocation(locationInfo)
        onAddLocation()
    }
}