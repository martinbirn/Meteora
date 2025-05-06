package org.meteora.domain.entities

data class WeatherInfo(
    val cityName: String,
    val temperatureCelsius: Double,
    val weatherDescription: String?
)