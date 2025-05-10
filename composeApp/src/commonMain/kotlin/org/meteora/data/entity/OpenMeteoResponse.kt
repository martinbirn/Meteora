package org.meteora.data.entity

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.toLocalDateTime
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.meteora.domain.entity.DailyWeatherInfo
import org.meteora.domain.entity.HourlyWeatherInfo
import org.meteora.domain.entity.LocationInfo
import org.meteora.domain.entity.WeatherInfo
import org.meteora.presentation.util.formatter.dayOfWeekFormatter

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
        val currentHour = Instant.fromEpochSeconds(current.time)
            .toLocalDateTime(TimeZone.currentSystemDefault()).time.hour
        val dailyList = toDailyList()
        val currentDay = dailyList[0]
        val currentDayHourlies = currentDay.hourlies.filter { it.hour >= currentHour }.let {
            it + dailyList[1].hourlies.take(24 - it.size)
        }

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
                humidity = current.relativeHumidity2m,
                uvIndex = currentDay.uvIndex
            ),
            weatherCode = hourly.weatherCode.getOrElse(currentIndex) { 0 },
            visibility = hourly.visibility.getOrElse(currentIndex) { 0.0 },
            precipitation = current.precipitation,
            sunrise = currentDay.sunrise,
            sunset = currentDay.sunset,
            windSpeed = hourly.windSpeed10m.getOrElse(currentIndex) { 0.0 },
            windDirection = hourly.windDirection10m.getOrElse(currentIndex) { 0 },
            windGusts = hourly.windGusts10m.getOrElse(currentIndex) { 0.0 },
            dailies = dailyList,
            hourlies = currentDayHourlies
        )
    }

    fun toDailyList(): List<DailyWeatherInfo> {
        val dailyList = ArrayList<DailyWeatherInfo>(daily.time.size)

        for (i in daily.time.indices) {
            val dayStart = daily.time[i]
            val dayEnd = if (i < daily.time.size - 1) daily.time[i + 1] else dayStart + 86400

            val startHourIndex = hourly.time.indexOfFirst { it >= dayStart }
            val endHourIndex = hourly.time.indexOfLast { it < dayEnd }

            if (startHourIndex != -1 && endHourIndex != -1) {
                val dayWeatherCodes = hourly.weatherCode.subList(
                    startHourIndex,
                    minOf(endHourIndex + 1, hourly.weatherCode.size)
                )
                // Get weather code for the day
                val weatherCode = getDailyWeatherCode(dayWeatherCodes)

                // Get hourly data for this day
                val hourlyDataForDay = (startHourIndex..endHourIndex).map { index ->
                    HourlyWeatherInfo(
                        index = index,
                        hour = Instant.fromEpochSeconds(hourly.time[index])
                            .toLocalDateTime(TimeZone.currentSystemDefault()).time.hour,
                        temp = hourly.temperature2m[index],
                        weatherCode = hourly.weatherCode[index],
                        windSpeed = hourly.windSpeed10m.getOrElse(index) { 0.0 },
                        windDirection = hourly.windDirection10m.getOrElse(index) { 0 },
                        windGusts = hourly.windGusts10m.getOrElse(index) { 0.0 }
                    )
                }

                dailyList.add(
                    DailyWeatherInfo(
                        dayOfWeek = Instant.fromEpochSeconds(dayStart)
                            .toLocalDateTime(TimeZone.currentSystemDefault()).date
                            .format(dayOfWeekFormatter),
                        tempMax = daily.temperature2mMax[i],
                        tempMin = daily.temperature2mMin[i],
                        weatherCode = weatherCode,
                        sunrise = daily.sunrise[i],
                        sunset = daily.sunset[i],
                        uvIndex = if (i < daily.uvIndexMax.size) daily.uvIndexMax[i] else 0.0,
                        hourlies = hourlyDataForDay
                    )
                )
            }
        }

        return dailyList
    }

    private fun getDailyWeatherCode(dayWeatherCodes: List<Int>): Int {
        val priorityMap = buildMap {
            listOf(
                // Thunderstorm
                95, 96, 99,
                // Heavy snow -> Snow grains
                75, 73, 71, 77,
                // Heavy rain -> Slight rain/showers
                65, 82, 63, 81, 61, 80,
                // Drizzle
                55, 53, 51,
                // Fog
                45, 48,
                // Clouds
                3, 2, 1,
                // Clear sky
                0
            ).forEachIndexed { index, code -> put(code, index) }
        }

        // min index - most priority
        return dayWeatherCodes.minByOrNull { code -> priorityMap[code] ?: Int.MAX_VALUE } ?: 0
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
    val visibility: List<Double>,

    @SerialName("wind_gusts_10m")
    val windGusts10m: List<Double>,

    @SerialName("wind_direction_10m")
    val windDirection10m: List<Int>,

    @SerialName("wind_speed_10m")
    val windSpeed10m: List<Double>
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
    val sunrise: List<Long>,

    @SerialName("uv_index_max")
    val uvIndexMax: List<Double> = emptyList()
) 