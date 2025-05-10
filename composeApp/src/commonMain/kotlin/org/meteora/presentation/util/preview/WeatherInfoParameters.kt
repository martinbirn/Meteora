package org.meteora.presentation.util.preview

import kotlinx.datetime.Clock
import kotlinx.datetime.DayOfWeek
import org.jetbrains.compose.ui.tooling.preview.PreviewParameterProvider
import org.meteora.domain.entity.DailyWeatherInfo
import org.meteora.domain.entity.HourlyWeatherInfo
import org.meteora.domain.entity.WeatherInfo

class WeatherInfoParameters : PreviewParameterProvider<WeatherInfo> {
    override val values = sequenceOf(
        WeatherInfo(
            location = WeatherInfo.Location(
                lat = 51.1,
                lon = 17.03,
                city = "Wrocław",
                country = "Poland"
            ),
            main = WeatherInfo.Main(
                temp = 13.5,
                feelsLike = 10.3,
                tempMin = 5.6,
                tempMax = 13.6,
                pressure = 1015.6,
                humidity = 40,
                uvIndex = 2.5,
            ),
            weatherCode = 0, // Clear sky
            visibility = 24000.0,
            precipitation = 2.3,
            sunrise = Clock.System.now().toEpochMilliseconds() - 21600,
            sunset = Clock.System.now().toEpochMilliseconds(),
            windSpeed = 0.0,
            windDirection = 0,
            windGusts = 0.0,
            dailies = createSampleDailies(),
            hourlies = createSampleHourlies(),
        ),
        WeatherInfo(
            location = WeatherInfo.Location(
                lat = 51.1,
                lon = 17.03,
                city = "Wrocław",
                country = "Poland"
            ),
            main = WeatherInfo.Main(
                temp = 10.2,
                feelsLike = 8.4,
                tempMin = 4.3,
                tempMax = 11.5,
                pressure = 1020.1,
                humidity = 65,
                uvIndex = 2.5,
            ),
            weatherCode = 3, // Clouds
            visibility = 18000.0,
            precipitation = 2.3,
            sunrise = Clock.System.now().toEpochMilliseconds() - 21600,
            sunset = Clock.System.now().toEpochMilliseconds(),
            windSpeed = 0.0,
            windDirection = 0,
            windGusts = 0.0,
            dailies = createSampleDailies(startWeatherCode = 3),
            hourlies = createSampleHourlies(startWeatherCode = 3)
        ),
        WeatherInfo(
            location = WeatherInfo.Location(
                lat = 51.1,
                lon = 17.03,
                city = "Wrocław",
                country = "Poland"
            ),
            main = WeatherInfo.Main(
                temp = 8.7,
                feelsLike = 6.2,
                tempMin = 2.9,
                tempMax = 9.3,
                pressure = 1018.2,
                humidity = 78,
                uvIndex = 2.5,
            ),
            weatherCode = 61, // Rain
            visibility = 12000.0,
            precipitation = 2.3,
            sunrise = Clock.System.now().toEpochMilliseconds() - 21600,
            sunset = Clock.System.now().toEpochMilliseconds(),
            windSpeed = 0.0,
            windDirection = 0,
            windGusts = 0.0,
            dailies = createSampleDailies(startWeatherCode = 61),
            hourlies = createSampleHourlies(startWeatherCode = 61)
        )
    )

    private fun createSampleDailies(startWeatherCode: Int = 0): List<DailyWeatherInfo> {
        val currentTimestamp = Clock.System.now().epochSeconds
        val oneDayInSeconds = 86400L

        return List(7) { dayIndex ->
            val weatherCode = when (dayIndex) {
                0 -> startWeatherCode
                1 -> if (startWeatherCode == 0) 1 else startWeatherCode
                2 -> if (startWeatherCode == 0) 2 else (startWeatherCode + 1) % 99
                else -> (startWeatherCode + dayIndex) % 99
            }

            DailyWeatherInfo(
                dayOfWeek = DayOfWeek.entries[dayIndex].name,
                tempMax = 15.0 - dayIndex * 0.5,
                tempMin = 5.0 - dayIndex * 0.3,
                weatherCode = weatherCode,
                sunrise = currentTimestamp + (dayIndex * oneDayInSeconds) + 21600, // +6 hours
                sunset = currentTimestamp + (dayIndex * oneDayInSeconds) + 64800,  // +18 hours
                uvIndex = 0.0,
                hourlies = createSampleHourlies(
                    startHour = 0,
                    count = 24,
                    startWeatherCode = weatherCode
                )
            )
        }
    }

    private fun createSampleHourlies(
        startHour: Int = 8,
        count: Int = 24,
        startWeatherCode: Int = 0
    ): List<HourlyWeatherInfo> {
        val baseTemp = 10.0
        return List(count) { index ->
            val hour = (startHour + index) % 24
            val weatherCodeOffset = index % 3
            HourlyWeatherInfo(
                index = index,
                hour = hour,
                temp = baseTemp + index % 5,
                weatherCode = (startWeatherCode + weatherCodeOffset) % 99,
                windSpeed = 5.0 + (index % 10),
                windDirection = (index * 10) % 360,
                windGusts = 8.0 + (index % 15)
            )
        }
    }
}