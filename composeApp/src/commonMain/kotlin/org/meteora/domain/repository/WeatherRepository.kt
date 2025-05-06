package org.meteora.domain.repository

import org.meteora.domain.entity.LocationInfo
import org.meteora.domain.entity.WeatherInfo

interface WeatherRepository {
    suspend fun getWeather(lat: Double, lon: Double): Result<WeatherInfo>
    suspend fun getLocationInfo(lat: Double, lon: Double): Result<LocationInfo>
}