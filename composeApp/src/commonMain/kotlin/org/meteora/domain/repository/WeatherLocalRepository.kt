package org.meteora.domain.repository

import kotlinx.coroutines.flow.Flow
import org.meteora.domain.entity.LocationInfo

interface WeatherLocalRepository {

    fun getAllLocationsFlow(): Flow<List<LocationInfo>>

    fun getLocationByCoordinates(latitude: Double, longitude: Double): LocationInfo?

    fun addLocation(location: LocationInfo)

    fun deleteLocationById(id: String)

    fun deleteAllLocations()
}
