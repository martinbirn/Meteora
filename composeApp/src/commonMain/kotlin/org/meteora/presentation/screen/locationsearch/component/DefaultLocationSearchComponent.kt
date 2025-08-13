package org.meteora.presentation.screen.locationsearch.component

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.pages.ChildPages
import com.arkivanov.decompose.router.pages.PagesNavigation
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.meteora.domain.entity.LocationInfo
import org.meteora.domain.entity.LocationInfoShort
import org.meteora.domain.repository.WeatherApiRepository
import org.meteora.presentation.screen.locationweathersheet.component.DefaultLocationWeatherSheetComponent
import org.meteora.presentation.decompose.bottomsheet.BottomSheetContentComponent
import org.meteora.presentation.decompose.bottomsheet.pages.navigation.bottomSheetPages
import org.meteora.presentation.decompose.bottomsheet.pages.navigation.pop
import org.meteora.presentation.decompose.bottomsheet.pages.navigation.pushNew
import org.meteora.presentation.screen.locationsearch.LocationSearchUiState
import org.meteora.util.coroutineScope

class DefaultLocationSearchComponent(
    componentContext: ComponentContext,
    private val weatherApiRepository: WeatherApiRepository,
    private val onNavigateBack: () -> Unit,
    private val onNavigateBackTo: (Int) -> Unit,
) : LocationSearchComponent, KoinComponent, ComponentContext by componentContext {

    private val coroutineScope = coroutineScope()

    private val _uiState = MutableStateFlow<LocationSearchUiState>(LocationSearchUiState.Idle)

    override val uiState: StateFlow<LocationSearchUiState> = _uiState

    private val bottomSheetPagesNavigation = PagesNavigation<SearchBottomSheetConfig>()

    override val bottomSheetPages: Value<ChildPages<SearchBottomSheetConfig, BottomSheetContentComponent>> =
        bottomSheetPages(
            source = bottomSheetPagesNavigation,
            serializer = SearchBottomSheetConfig.serializer(),
            childFactory = ::child,
        )

    private val _input = MutableStateFlow("")
    override val inputText: StateFlow<String> = _input

    // Temporary map for id -> full LocationInfo mapping
    private var keyLocationMap: Map<String, LocationInfo> = emptyMap()

    init {
        coroutineScope.launch {
            _input
                .debounce(500)
                .collectLatest { text ->
                    if (text.isBlank()) {
                        _uiState.value = LocationSearchUiState.Idle
                        return@collectLatest
                    }

                    _uiState.value = LocationSearchUiState.Loading
                    weatherApiRepository.searchLocations(query = text)
                        .onSuccess { locations ->
                            if (locations.isEmpty()) {
                                keyLocationMap = emptyMap()
                                _uiState.value = LocationSearchUiState.NoResult
                            } else {
                                val map = mutableMapOf<String, LocationInfo>()
                                val shortList = locations.map { loc ->
                                    map[loc.id] = loc
                                    loc.toShortInfo()
                                }
                                keyLocationMap = map
                                _uiState.value = LocationSearchUiState.Content(shortList)
                            }
                        }
                        .onFailure { ex ->
                            _uiState.value = LocationSearchUiState.Error(ex)
                        }
                }
        }
    }

    private fun child(
        config: SearchBottomSheetConfig,
        componentContext: ComponentContext
    ): BottomSheetContentComponent =
        when (config) {
            is SearchBottomSheetConfig.LocationWeather ->
                DefaultLocationWeatherSheetComponent(
                    componentContext = componentContext,
                    locationInfo = config.locationInfo,
                    weatherApiRepository = get(),
                    weatherLocalRepository = get(),
                    onNavigateBack = ::dismissBottomSheet,
                    onAddLocation = {
                        bottomSheetPagesNavigation.pop { onNavigateBackTo(0) }
                    },
                )
        }

    override fun updateInput(input: String) {
        _input.value = input
    }

    override fun clearInput() {
        _input.value = ""
        _uiState.value = LocationSearchUiState.Idle
    }

    override fun navigateToLocationWeather(location: LocationInfoShort) {
        keyLocationMap[location.id]?.let { location ->
            bottomSheetPagesNavigation.pushNew(SearchBottomSheetConfig.LocationWeather(location))
        }
    }

    override fun navigateBack() {
        onNavigateBack()
    }

    override fun dismissBottomSheet() {
        bottomSheetPagesNavigation.pop()
    }

    @Serializable
    sealed class SearchBottomSheetConfig {

        @Serializable
        data class LocationWeather(val locationInfo: LocationInfo) : SearchBottomSheetConfig()
    }
}