package org.meteora.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import org.meteora.domain.repository.WeatherApiRepository
import org.meteora.util.MeteoraJson
import org.meteora.util.Resource
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class WeatherApiRepositoryImplTest {

    private fun createMockClient(
        responseContent: String,
        responseStatus: HttpStatusCode,
        isError: Boolean = false
    ): HttpClient {
        val mockEngine = MockEngine { request ->
            // assertEquals("https://nominatim.openstreetmap.org/search?q=Wroclaw&format=json&limit=1&addressdetails=1", request.url.toString())
            if (isError) {
                respond(
                    content = responseContent,
                    status = responseStatus,
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
                )
            } else {
                respond(
                    content = responseContent,
                    status = responseStatus,
                    headers = headersOf(HttpHeaders.ContentType, "application/json")
                )
            }
        }
        return HttpClient(mockEngine) {
            install(ContentNegotiation) {
                json(MeteoraJson)
            }
        }
    }

    @Test
    fun `searchLocations returns success with list of locations on successful API response`() =
        runTest {
            val mockJsonResponse = Resource(
                path = "src/commonTest/resources/search_locations_success_response.json"
            ).readText().replace("\n", "")

            val mockHttpClient = createMockClient(mockJsonResponse, HttpStatusCode.OK)
            val repository: WeatherApiRepository = WeatherApiRepositoryImpl(mockHttpClient)
            val query = "Wroclaw"
            val limit = 1

            // Act
            val result = repository.searchLocations(query, limit)

            // Assert
            assertTrue(
                result.isSuccess,
                "Result should be success. Actual: ${result.exceptionOrNull()?.message}"
            )
            val locations = result.getOrNull()
            assertFalse(locations.isNullOrEmpty(), "Locations list should not be null or empty")
            assertEquals(1, locations.size, "Locations list should contain one item")

            val location = locations.first()
            assertNotNull(location, "Location object should not be null")

            assertEquals("Wroclaw", location.locality, "Location city should be Wrocław")
            assertEquals("Poland", location.country, "Location country should be Poland")
            assertEquals("pl", location.countryCode, "Location country code should be pl")
            assertEquals(
                51.0999260,
                location.latitude,
                0.000001,
                "Latitude mismatch"
            )
            assertEquals(
                17.0400160,
                location.longitude,
                0.000001,
                "Longitude mismatch"
            )
            assertEquals(
                "7, Plac Konstytucji 3 Maja, Trójkąt, Przedmieście Oławskie, Wrocław, Lower Silesian Voivodeship, 50-083, Poland",
                location.displayName,
                "Display name mismatch"
            )
        }

    @Test
    fun `searchLocations returns failure on API error`() = runTest {
        // Arrange
        val mockErrorResponse = """{"error": "Unable to geocode"}"""
        val mockHttpClient =
            createMockClient(mockErrorResponse, HttpStatusCode.BadRequest, isError = true)
        val repository: WeatherApiRepository = WeatherApiRepositoryImpl(mockHttpClient)
        val query = "InvalidQuery!@#"
        val limit = 5

        // Act
        val result = repository.searchLocations(query, limit)

        // Assert
        assertTrue(result.isFailure, "Result should be failure")
        val exception = result.exceptionOrNull()
        assertTrue(exception is Throwable, "Exception should be a Throwable")

        assertTrue(
            exception.message?.contains("searchLocations: Bad Request") == true,
            "Exception message should indicate a bad request"
        )
    }

    @Test
    fun `searchLocations returns failure on network or parsing error`() = runTest {
        // Arrange
        val mockMalformedJsonResponse = """[{"lat": "not_a_double"}]"""
        val mockHttpClient = createMockClient(mockMalformedJsonResponse, HttpStatusCode.OK)
        val repository: WeatherApiRepository = WeatherApiRepositoryImpl(mockHttpClient)
        val query = "SomeQuery"
        val limit = 1

        // Act
        val result = repository.searchLocations(query, limit)

        // Assert
        assertTrue(result.isFailure, "Result should be failure due to parsing error")
        val exception = result.exceptionOrNull()
        assertTrue(exception is Exception, "Exception should be present")
    }
}
