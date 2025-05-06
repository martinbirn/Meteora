package org.meteora.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import org.meteora.data.entity.NominatimResponse
import org.meteora.data.entity.OpenMeteoResponse
import org.meteora.domain.entity.LocationInfo
import org.meteora.domain.entity.WeatherInfo
import org.meteora.domain.repository.WeatherRepository
import kotlin.coroutines.cancellation.CancellationException

class WeatherRepositoryImpl(
    private val client: HttpClient
) : WeatherRepository {

    override suspend fun getWeather(lat: Double, lon: Double): Result<WeatherInfo> =
        coroutineScope {
            try {
                val weatherDeferred = async {
                    client.get("https://api.open-meteo.com/v1/forecast") {
                        parameter("latitude", lat)
                        parameter("longitude", lon)
                        parameter("daily", "temperature_2m_max,temperature_2m_min,sunset,sunrise")
                        parameter("hourly", "temperature_2m,weather_code,visibility")
                        parameter(
                            "current",
                            "temperature_2m,apparent_temperature,precipitation,relative_humidity_2m,pressure_msl"
                        )
                        parameter("timezone", "auto")
                        parameter("forecast_days", 1)
                        parameter("timeformat", "unixtime")
                    }
                }
                val locationDeferred = async {
                    getLocationInfo(lat, lon)
                }

                val weatherResponse = weatherDeferred.await()
                val locationResult = locationDeferred.await()

                if (weatherResponse.status != HttpStatusCode.OK) {
                    return@coroutineScope Result.failure(Throwable("getWeather2: ${weatherResponse.status.description}"))
                }
                locationResult.onFailure {
                    return@coroutineScope Result.failure(it)
                }

                val weatherData = weatherResponse.body<OpenMeteoResponse>()
                val locationInfo = locationResult.getOrThrow()

                Result.success(weatherData.toDomain(locationInfo))

            } catch (ex: CancellationException) {
                throw ex
            } catch (ex: Exception) {
                Result.failure(ex)
            }
        }

    override suspend fun getLocationInfo(lat: Double, lon: Double): Result<LocationInfo> = try {
        val response = client.get("https://nominatim.openstreetmap.org/reverse") {
            parameter("lat", lat)
            parameter("lon", lon)
            parameter("format", "json")
        }
        when (response.status) {
            HttpStatusCode.OK -> {
                val body = response.body<NominatimResponse>()
                Result.success(body.toDomain())
            }

            else -> Result.failure(Throwable("getLocationInfo: ${response.status.description}"))
        }
    } catch (ex: CancellationException) {
        throw ex
    } catch (ex: Exception) {
        Result.failure(ex)
    }
}
