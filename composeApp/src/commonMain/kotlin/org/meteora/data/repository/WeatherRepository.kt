package org.meteora.data.repository

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode
import org.meteora.BuildKonfig
import org.meteora.data.api.WeatherResponse
import kotlin.coroutines.cancellation.CancellationException

class WeatherRepository(
    private val client: HttpClient
) {

    suspend fun getWeather(city: String) = try {
        val response = client.get("https://api.openweathermap.org/data/2.5/weather") {
            parameter("q", city)
            parameter("appid", BuildKonfig.WEATHER_API_KEY)
            parameter("units", "metric")
        }
        when (response.status) {
            HttpStatusCode.OK -> {
                val body = response.body<WeatherResponse>()
                Result.success(body.toDomain())
            }

            else -> Result.failure(Throwable("getWeather: ${response.status.description}"))
        }
    } catch (ex: CancellationException) {
        throw ex
    } catch (ex: Exception) {
        Result.failure(ex)
    }
}
