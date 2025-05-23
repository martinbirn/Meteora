package org.meteora.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.meteora.cache.Database
import org.meteora.data.entity.DbLocation
import org.meteora.domain.entity.LocationInfo
import org.meteora.domain.repository.WeatherLocalRepository

internal class WeatherLocalRepositoryImpl(
    private val database: Database
) : WeatherLocalRepository {

    override fun getAllLocationsFlow(): Flow<List<LocationInfo>> {
        return database.getAllLocations().map { list ->
            list.map(DbLocation::toDomain)
        }
    }

    override fun getLocationByCoordinates(latitude: Double, longitude: Double): LocationInfo? {
        return database.getLocationByCoordinates(latitude, longitude)?.toDomain()
    }

    override fun addLocation(location: LocationInfo) {
        val dbLocation = DbLocation.fromDomain(location)
        database.insertLocation(location = dbLocation)
    }

    override fun deleteLocationById(id: String) {
        database.deleteLocationById(id)
    }

    override fun deleteAllLocations() {
        database.deleteAllLocations()
    }
}
