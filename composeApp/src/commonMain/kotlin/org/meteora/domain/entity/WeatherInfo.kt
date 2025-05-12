package org.meteora.domain.entity

typealias WeatherCode = Int
typealias DirectionAngle = Int

data class WeatherInfo(
    val location: Location,
    val main: Main,
    val weatherCode: WeatherCode,
    val visibility: Double,
    val precipitation: Double,
    val sunrise: Long,
    val sunset: Long,
    val windSpeed: Double,
    val windDirection: DirectionAngle,
    val windGusts: Double,
    val dailies: List<DailyWeatherInfo>,
    val hourlies: List<HourlyWeatherInfo>
) {
    data class Location(
        val lat: Double,
        val lon: Double,
        val locality: String?,
        val country: String?,
        val countryCode: String?,
    )

    data class Main(
        val temp: Double,
        val feelsLike: Double,
        val tempMin: Double,
        val tempMax: Double,
        val pressure: Double,
        val humidity: Int,
        val uvIndex: Double,
    )

    data class Weather(
        val main: String,
        val description: String,
        val icon: String,
    )
}

data class DailyWeatherInfo(
    val dayOfWeek: String,
    val tempMax: Double,
    val tempMin: Double,
    val weatherCode: Int,
    val sunrise: Long,
    val sunset: Long,
    val uvIndex: Double,
    val hourlies: List<HourlyWeatherInfo> = emptyList()
)

data class HourlyWeatherInfo(
    val index: Int,
    val hour: Int,
    val temp: Double,
    val weatherCode: WeatherCode,
    val windSpeed: Double = 0.0,
    val windDirection: Int = 0,
    val windGusts: Double = 0.0
)
