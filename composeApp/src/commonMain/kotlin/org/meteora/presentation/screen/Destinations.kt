package org.meteora.presentation.screen

import kotlinx.serialization.Serializable
import org.meteora.domain.entity.LatLong

@Serializable
object LocationsDestination

@Serializable
data class LocationWeatherDestination(val latLong: LatLong)

@Serializable
object SearchLocationDestination
