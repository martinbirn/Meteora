package org.meteora.domain.repository

import kotlinx.coroutines.flow.Flow
import org.meteora.domain.entity.LocationInfo

interface WeatherLocalRepository {

    fun getAllLocationsFlow(): Flow<List<LocationInfo>>

    fun addLocation(location: LocationInfo)
}
