package org.meteora.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.meteora.data.util.WEATHER_IMAGE_URL
import org.meteora.data.util.WEATHER_IMAGE_URL_POSTFIX_2X
import org.meteora.domain.entity.LocationInfo
import org.meteora.domain.entity.WeatherInfo

@Serializable
data class OpenMeteoResponse(
    @SerialName("latitude")
    val latitude: Double,
    
    @SerialName("longitude")
    val longitude: Double,
    
    @SerialName("timezone")
    val timezone: String,
    
    @SerialName("timezone_abbreviation")
    val timezoneAbbreviation: String,
    
    @SerialName("current")
    val current: CurrentWeather,
    
    @SerialName("hourly")
    val hourly: HourlyWeather,
    
    @SerialName("daily")
    val daily: DailyWeather
) {
    fun toDomain(locationInfo: LocationInfo): WeatherInfo {
        val currentIndex = hourly.time.indexOfFirst { it >= current.time }.let {
            if (it == -1) 0 else it
        }
        
        val weatherCode = hourly.weatherCode.getOrNull(currentIndex) ?: 0
            
        return WeatherInfo(
            location = WeatherInfo.Location(
                lat = latitude,
                lon = longitude,
                city = locationInfo.city,
                country = locationInfo.country
            ),
            main = WeatherInfo.Main(
                temp = current.temperature2m,
                feelsLike = current.apparentTemperature,
                tempMin = daily.temperature2mMin.firstOrNull() ?: 0.0,
                tempMax = daily.temperature2mMax.firstOrNull() ?: 0.0,
                pressure = current.pressureMsl,
                humidity = current.relativeHumidity2m
            ),
            weather = mapWeatherCode(weatherCode)
        )
    }

    // weather code table - https://open-meteo.com/en/docs#weather_variable_documentation
    private fun mapWeatherCode(code: Int): WeatherInfo.Weather {
        val (main, description, icon) = when (code) {
            0 -> Triple("Clear", "clear sky", "01d")
            1 -> Triple("Clear", "few clouds", "02d")
            2 -> Triple("Clouds", "scattered clouds", "03d")
            3 -> Triple("Clouds", "broken clouds", "04d")
            45, 48 -> Triple("Fog", "fog", "50d")
            51, 53, 55 -> Triple("Drizzle", "drizzle", "09d")
            61, 63, 65 -> Triple("Rain", "rain", "10d")
            71, 73, 75, 77 -> Triple("Snow", "snow", "13d")
            80, 81, 82 -> Triple("Rain", "rain showers", "09d")
            95, 96, 99 -> Triple("Thunderstorm", "thunderstorm", "11d")
            else -> Triple("Unknown", "unknown", "01d")
        }
        
        return WeatherInfo.Weather(
            main = main,
            description = description,
            icon = WEATHER_IMAGE_URL + icon + WEATHER_IMAGE_URL_POSTFIX_2X
        )
    }
}

@Serializable
data class CurrentWeather(
    @SerialName("time")
    val time: Long,
    
    @SerialName("temperature_2m")
    val temperature2m: Double,
    
    @SerialName("apparent_temperature")
    val apparentTemperature: Double,
    
    @SerialName("precipitation")
    val precipitation: Double,
    
    @SerialName("relative_humidity_2m")
    val relativeHumidity2m: Int,
    
    @SerialName("pressure_msl")
    val pressureMsl: Double
)

@Serializable
data class HourlyWeather(
    @SerialName("time")
    val time: List<Long>,
    
    @SerialName("temperature_2m")
    val temperature2m: List<Double>,
    
    @SerialName("weather_code")
    val weatherCode: List<Int>,
    
    @SerialName("visibility")
    val visibility: List<Double>
)

@Serializable
data class DailyWeather(
    @SerialName("time")
    val time: List<Long>,
    
    @SerialName("temperature_2m_max")
    val temperature2mMax: List<Double>,
    
    @SerialName("temperature_2m_min")
    val temperature2mMin: List<Double>,
    
    @SerialName("sunset")
    val sunset: List<Long>,
    
    @SerialName("sunrise")
    val sunrise: List<Long>
) 