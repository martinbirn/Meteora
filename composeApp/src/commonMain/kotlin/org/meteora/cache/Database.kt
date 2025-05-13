package org.meteora.cache

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.meteora.data.entity.DbLocation

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = MeteoraDatabase(databaseDriverFactory.createDriver())
    private val dbQuery = database.meteoraDatabaseQueries

    internal fun getAllLocations(): Flow<List<DbLocation>> {
        return dbQuery.selectAll().asFlow().mapToList(Dispatchers.IO).map { list ->
            list.map {
                DbLocation(
                    latitude = it.latitude,
                    longitude = it.longitude,
                    locality = it.locality,
                    country = it.country,
                    countryCode = it.countryCode,
                    displayName = it.displayName
                )
            }
        }
    }

    internal fun getLocationById(id: Long): DbLocation? {
        return dbQuery.selectById(id).executeAsOneOrNull()?.let {
            DbLocation(
                latitude = it.latitude,
                longitude = it.longitude,
                locality = it.locality,
                country = it.country,
                countryCode = it.countryCode,
                displayName = it.displayName
            )
        }
    }

    internal fun getLocationByCoordinates(latitude: Double, longitude: Double): DbLocation? {
        return dbQuery.selectByCoordinates(latitude, longitude).executeAsOneOrNull()?.let {
            DbLocation(
                latitude = it.latitude,
                longitude = it.longitude,
                locality = it.locality,
                country = it.country,
                countryCode = it.countryCode,
                displayName = it.displayName
            )
        }
    }

    internal fun insertLocation(location: DbLocation) {
        dbQuery.insertLocation(
            latitude = location.latitude,
            longitude = location.longitude,
            locality = location.locality,
            country = location.country,
            countryCode = location.countryCode,
            displayName = location.displayName
        )
    }

    internal fun deleteLocation(id: Long) {
        dbQuery.deleteLocation(id)
    }

    internal fun clearDatabase() {
        dbQuery.transaction {
            dbQuery.selectAll().executeAsList().forEach {
                dbQuery.deleteLocation(it.id)
            }
        }
    }
}
