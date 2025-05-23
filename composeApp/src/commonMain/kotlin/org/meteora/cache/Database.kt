package org.meteora.cache

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.db.SqlDriver
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.meteora.data.entity.DbLocation

internal class Database(sqlDriver: SqlDriver) {
    private val database = MeteoraDatabase(sqlDriver)
    private val dbQuery = database.meteoraDatabaseQueries

    internal fun getAllLocations(): Flow<List<DbLocation>> {
        return dbQuery.selectAll().asFlow().mapToList(Dispatchers.IO).map { list ->
            list.map {
                DbLocation(
                    id = it.id,
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

    internal fun getLocationById(id: String): DbLocation? {
        return dbQuery.selectById(id).executeAsOneOrNull()?.let {
            DbLocation(
                id = it.id,
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
                id = it.id,
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
            id = location.id,
            latitude = location.latitude,
            longitude = location.longitude,
            locality = location.locality,
            country = location.country,
            countryCode = location.countryCode,
            displayName = location.displayName
        )
    }

    internal fun deleteLocationById(id: String) {
        dbQuery.deleteLocationById(id)
    }

    internal fun deleteAllLocations() {
        dbQuery.deleteAllLocations()
    }
}
