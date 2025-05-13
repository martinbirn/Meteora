package org.meteora.data.repository

import org.meteora.data.entity.DbLocation
import org.meteora.domain.entity.LocationInfo
import org.meteora.domain.repository.WeatherLocalRepository

class WeatherLocalRepositoryImpl() : WeatherLocalRepository {

    override fun addLocation(location: LocationInfo) {
        val dbLocation = DbLocation.fromDomain(location)
    }
}
