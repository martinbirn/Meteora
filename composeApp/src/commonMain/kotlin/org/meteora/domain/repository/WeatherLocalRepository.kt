package org.meteora.domain.repository

import org.meteora.domain.entity.LocationInfo

interface WeatherLocalRepository {

    fun addLocation(location: LocationInfo)
}
