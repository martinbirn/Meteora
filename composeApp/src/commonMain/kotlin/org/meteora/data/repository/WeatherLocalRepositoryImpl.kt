package org.meteora.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.meteora.cache.Database
import org.meteora.cache.DatabaseDriverFactory
import org.meteora.data.entity.DbLocation
import org.meteora.domain.entity.LocationInfo
import org.meteora.domain.repository.WeatherLocalRepository

class WeatherLocalRepositoryImpl(
    databaseDriverFactory: DatabaseDriverFactory
) : WeatherLocalRepository {

    private val database = Database(databaseDriverFactory)

    override fun getAllLocationsFlow(): Flow<List<LocationInfo>> {
        return database.getAllLocations().map { list ->
            list.map(DbLocation::toDomain)
        }
    }

    override fun addLocation(location: LocationInfo) {
        val dbLocation = DbLocation.fromDomain(location)
        database.insertLocation(location = dbLocation)
    }
}
