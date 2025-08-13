package org.meteora.presentation.decompose

import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.meteora.domain.entity.LocationInfo
import org.meteora.domain.repository.WeatherApiRepository
import org.meteora.domain.repository.WeatherLocalRepository
import org.meteora.presentation.decompose.bottomsheet.BottomSheetContentComponent
import org.meteora.presentation.decompose.bottomsheet.BottomSheetContentState

interface LocationWeatherSheetComponent : BottomSheetContentComponent {
    val state: StateFlow<LocationWeatherSheet>
    val weatherComponent: LocationWeatherComponent
    val locationInfo: LocationInfo

    fun navigateBack()

    // Add the selected location to the local repository
    fun addLocation(locationInfo: LocationInfo)
}

class DefaultLocationWeatherSheetComponent(
    componentContext: ComponentContext,
    override val locationInfo: LocationInfo,
    dialogState: LocationWeatherSheet,
    weatherApiRepository: WeatherApiRepository,
    private val weatherLocalRepository: WeatherLocalRepository,
    private val onNavigateBack: () -> Unit,
    private val onAddLocation: () -> Unit,
) : LocationWeatherSheetComponent, ComponentContext by componentContext {

    override val state = MutableStateFlow(dialogState)
    override val bottomSheetContentState = state

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

data class LocationWeatherSheet(
    override val isDismissAllowed: Boolean,
) : BottomSheetContentState
