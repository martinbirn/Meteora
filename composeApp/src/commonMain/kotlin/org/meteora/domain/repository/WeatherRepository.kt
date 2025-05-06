package org.meteora.domain.repository

import org.meteora.domain.entity.WeatherInfo

interface WeatherRepository {
    suspend fun getWeather(lat: Double, lon: Double): Result<WeatherInfo>
    suspend fun getWeather(city: String): Result<WeatherInfo>
}