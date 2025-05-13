package org.meteora.presentation.screen

import kotlinx.serialization.Serializable
import org.meteora.domain.entity.LocationInfo

@Serializable
object LocationsDestination

@Serializable
data class LocationWeatherDestination(val locationInfo: LocationInfo)

@Serializable
object SearchLocationDestination
