package org.meteora.presentation.weather

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.icerock.moko.permissions.PermissionsController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.meteora.data.repository.WeatherRepository

class WeatherViewModel(
    permissionsController: PermissionsController,
    private val weatherRepository: WeatherRepository
) : ViewModel() {

    private val _state = MutableStateFlow(State())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            weatherRepository.getWeather("Wroclaw").onSuccess { weatherInfo ->
                _state.update { it.copy(temp = weatherInfo.temperatureCelsius) }
            }.onFailure { error ->
                _state.update { it.copy(error = error.message) }
            }
        }
    }

    data class State(
        val temp: Double = 0.0,
        val error: String? = null
    )
}