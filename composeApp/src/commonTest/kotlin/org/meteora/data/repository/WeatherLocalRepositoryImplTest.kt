package org.meteora.data.repository

import app.cash.sqldelight.db.SqlDriver
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.meteora.cache.Database
import org.meteora.domain.entity.LocationInfo
import org.meteora.domain.repository.WeatherLocalRepository
import org.meteora.util.RobolectricTests
import org.meteora.util.getDatabaseDriverFactory
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlin.uuid.Uuid

class WeatherLocalRepositoryImplTest : RobolectricTests() {

    private lateinit var driver: SqlDriver
    private lateinit var database: Database
    private lateinit var repository: WeatherLocalRepository

    @BeforeTest
    fun setUp() {
        driver = getDatabaseDriverFactory().createDriver()
        database = Database(driver)
        repository = WeatherLocalRepositoryImpl(database)
    }

    @AfterTest
    fun tearDown() {
        runTest {
            database.deleteAllLocations()
        }
        driver.close()
    }

    @Test
    fun `getAllLocationsFlow initially returns empty list`() = runTest {
        val locations = repository.getAllLocationsFlow().first()
        assertTrue(locations.isEmpty(), "Initially, locations list should be empty")
    }

    @Test
    fun `addLocation adds location and getAllLocationsFlow emits it`() = runTest {
        val location1 = LocationInfo(
            id = Uuid.random().toString(),
            latitude = 10.0,
            longitude = 20.0,
            locality = "City1",
            country = "Country1",
            countryCode = "C1",
            displayName = "Display1"
        )
        repository.addLocation(location1)

        val locations = repository.getAllLocationsFlow().first()
        assertEquals(1, locations.size, "Locations list should contain one item")
        val retrievedLocation = locations.first()
        assertEquals(location1.latitude, retrievedLocation.latitude)
        assertEquals(location1.longitude, retrievedLocation.longitude)
        assertEquals(location1.locality, retrievedLocation.locality)
        assertEquals(location1.country, retrievedLocation.country)
        assertEquals(location1.countryCode, retrievedLocation.countryCode)
        assertEquals(location1.displayName, retrievedLocation.displayName)


        val location2 = LocationInfo(
            id = Uuid.random().toString(),
            latitude = 30.0,
            longitude = 40.0,
            locality = "City2",
            country = "Country2",
            countryCode = "C2",
            displayName = "Display2"
        )
        repository.addLocation(location2)

        val updatedLocations = repository.getAllLocationsFlow().first()
        assertEquals(2, updatedLocations.size, "Locations list should contain two items")
        assertTrue(updatedLocations.any { it.latitude == location1.latitude && it.longitude == location1.longitude })
        assertTrue(updatedLocations.any { it.latitude == location2.latitude && it.longitude == location2.longitude })
    }

    @Test
    fun `addLocation updates existing location if IDs match`() =
        runTest {
            val existingId = Uuid.random().toString()

            val initialLocation = LocationInfo(
                id = existingId,
                latitude = 10.0,
                longitude = 20.0,
                locality = "City1",
                country = "Country1",
                countryCode = "C1",
                displayName = "Initial Display"
            )
            repository.addLocation(initialLocation)

            val updatedLocationData = LocationInfo(
                id = existingId,
                latitude = 10.0,
                longitude = 20.0,
                locality = "City1 Updated",
                country = "Country1",
                countryCode = "C1",
                displayName = "Updated Display"
            )
            // INSERT OR REPLACE
            repository.addLocation(updatedLocationData)

            val locations = repository.getAllLocationsFlow().first()
            assertEquals(1, locations.size, "Should still be one location due to update")
            val resultLocation = locations.first()
            assertEquals(existingId, resultLocation.id)
            assertEquals("City1 Updated", resultLocation.locality)
            assertEquals("Updated Display", resultLocation.displayName)
        }


    @Test
    fun `getLocationByCoordinates returns correct location or null`() = runTest {
        val location1 = LocationInfo(
            id = Uuid.random().toString(),
            latitude = 10.0,
            longitude = 20.0,
            locality = "City1",
            country = "Country1",
            countryCode = "C1",
            displayName = "Display1"
        )
        val location2 = LocationInfo(
            id = Uuid.random().toString(),
            latitude = 30.0,
            longitude = 40.0,
            locality = "City2",
            country = "Country2",
            countryCode = "C2",
            displayName = "Display2"
        )
        repository.addLocation(location1)
        repository.addLocation(location2)

        val foundLocation = repository.getLocationByCoordinates(10.0, 20.0)
        assertNotNull(foundLocation, "Location1 should be found")
        assertEquals(location1.displayName, foundLocation.displayName)

        val notFoundLocation = repository.getLocationByCoordinates(50.0, 60.0)
        assertNull(notFoundLocation, "Location with non-existing coordinates should not be found")
    }

    @Test
    fun `deleteLocation removes location`() = runTest {
        val location1 = LocationInfo(
            id = Uuid.random().toString(),
            latitude = 10.0,
            longitude = 20.0,
            locality = "City1",
            country = "Country1",
            countryCode = "C1",
            displayName = "Display1"
        )
        val location2 = LocationInfo(
            id = Uuid.random().toString(),
            latitude = 30.0,
            longitude = 40.0,
            locality = "City2",
            country = "Country2",
            countryCode = "C2",
            displayName = "Display2"
        )
        repository.addLocation(location1)
        repository.addLocation(location2)

        var locations = repository.getAllLocationsFlow().first()
        assertEquals(2, locations.size)

        repository.deleteLocationById(location1.id)
        locations = repository.getAllLocationsFlow().first()
        assertEquals(1, locations.size, "Location list should contain one item after deletion")
        assertFalse(
            locations.any { it.latitude == location1.latitude && it.longitude == location1.longitude },
            "Deleted location should not be present"
        )
        assertTrue(
            locations.any { it.latitude == location2.latitude && it.longitude == location2.longitude },
            "Other location should still be present"
        )
    }
}
