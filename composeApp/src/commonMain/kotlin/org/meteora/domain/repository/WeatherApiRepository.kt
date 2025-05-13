package org.meteora.domain.repository

import org.meteora.domain.entity.LocationInfo
import org.meteora.domain.entity.WeatherInfo

interface WeatherApiRepository {
    suspend fun getWeather(lat: Double, lon: Double): Result<WeatherInfo>
    suspend fun findLocation(lat: Double, lon: Double): Result<LocationInfo>
    suspend fun searchLocations(query: String, limit: Int = 10): Result<List<LocationInfo>>
}
