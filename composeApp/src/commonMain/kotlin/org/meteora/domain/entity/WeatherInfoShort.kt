package org.meteora.domain.entity

data class WeatherInfoShort(
    val key: String,
    val name: String,
    val temp: Double,
    val tempMin: Double,
    val tempMax: Double,
    val weatherCode: WeatherCode,
    val lastUpdate: Long,
)
