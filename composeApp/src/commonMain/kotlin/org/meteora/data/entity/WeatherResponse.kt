package org.meteora.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.meteora.data.util.WEATHER_IMAGE_URL
import org.meteora.data.util.WEATHER_IMAGE_URL_POSTFIX_2X
import org.meteora.domain.entity.WeatherInfo

@Serializable
data class WeatherResponse(
    @SerialName("coord") val coord: Coord,
    @SerialName("weather") val weather: List<Weather>,
    @SerialName("base") val base: String,
    @SerialName("main") val main: Main,
    @SerialName("visibility") val visibility: Int,
    @SerialName("wind") val wind: Wind,
    @SerialName("clouds") val clouds: Clouds,
    @SerialName("dt") val dt: Long,
    @SerialName("sys") val sys: Sys,
    @SerialName("timezone") val timezone: Int,
    @SerialName("id") val id: Long,
    @SerialName("name") val name: String,
    @SerialName("cod") val cod: Int
) {
    @Serializable
    data class Coord(
        @SerialName("lon") val lon: Double,
        @SerialName("lat") val lat: Double
    )

    @Serializable
    data class Weather(
        @SerialName("id") val id: Int,
        @SerialName("main") val main: String,
        @SerialName("description") val description: String,
        @SerialName("icon") val icon: String
    )

    @Serializable
    data class Main(
        @SerialName("temp") val temp: Double,
        @SerialName("feels_like") val feelsLike: Double,
        @SerialName("temp_min") val tempMin: Double,
        @SerialName("temp_max") val tempMax: Double,
        @SerialName("pressure") val pressure: Int,
        @SerialName("humidity") val humidity: Int,
        @SerialName("sea_level") val seaLevel: Int? = null,
        @SerialName("grnd_level") val grndLevel: Int? = null
    )

    @Serializable
    data class Wind(
        @SerialName("speed") val speed: Double,
        @SerialName("deg") val deg: Int
    )

    @Serializable
    data class Clouds(
        @SerialName("all") val all: Int
    )

    @Serializable
    data class Sys(
        @SerialName("type") val type: Int,
        @SerialName("id") val id: Int,
        @SerialName("country") val country: String,
        @SerialName("sunrise") val sunrise: Long,
        @SerialName("sunset") val sunset: Long
    )

    fun toDomain(): WeatherInfo {
        return WeatherInfo(
            location = WeatherInfo.Location(
                lat = coord.lat,
                lon = coord.lon,
                city = name,
                country = sys.country
            ),
            main = WeatherInfo.Main(
                temp = main.temp.toInt(),
                feelsLike = main.feelsLike,
                tempMin = main.tempMin.toInt(),
                tempMax = main.tempMax.toInt(),
                pressure = main.pressure,
                humidity = main.humidity
            ),
            weather = weather.firstOrNull()?.let {
                WeatherInfo.Weather(
                    main = it.main,
                    description = it.description,
                    icon = WEATHER_IMAGE_URL + it.icon + WEATHER_IMAGE_URL_POSTFIX_2X
                )
            }
        )
    }
}