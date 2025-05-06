package org.meteora.domain.entity

data class WeatherInfo(
    val location: Location,
    val main: Main,
    val weather: Weather?,
) {
    data class Location(
        val lat: Double,
        val lon: Double,
        val city: String,
        val country: String,
    )

    data class Main(
        val temp: Double,
        val feelsLike: Double,
        val tempMin: Double,
        val tempMax: Double,
        val pressure: Double,
        val humidity: Int,
    )

    data class Weather(
        val main: String,
        val description: String,
        val icon: String
    )
}