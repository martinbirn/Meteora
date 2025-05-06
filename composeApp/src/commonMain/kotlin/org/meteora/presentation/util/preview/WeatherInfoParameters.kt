package org.meteora.presentation.util.preview

import kotlinx.datetime.Clock
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
                humidity = 40
            ),
            weatherCode = 0, // Clear sky
            dailies = createSampleDailies(),
            hourlies = createSampleHourlies()
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
                humidity = 65
            ),
            weatherCode = 3, // Clouds
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
                humidity = 78
            ),
            weatherCode = 61, // Rain
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
                date = currentTimestamp + (dayIndex * oneDayInSeconds),
                tempMax = 15.0 - dayIndex * 0.5,
                tempMin = 5.0 - dayIndex * 0.3,
                weatherCode = weatherCode,
                sunrise = currentTimestamp + (dayIndex * oneDayInSeconds) + 21600, // +6 hours
                sunset = currentTimestamp + (dayIndex * oneDayInSeconds) + 64800,  // +18 hours
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
        return List(count) { hourIndex ->
            val hour = (startHour + hourIndex) % 24
            val weatherCode = when (hourIndex) {
                in 0..2 -> startWeatherCode
                in 3..5 -> (startWeatherCode + 1) % 99
                in 6..8 -> (startWeatherCode + 2) % 99
                else -> (startWeatherCode + hourIndex % 5) % 99
            }

            HourlyWeatherInfo(
                index = hourIndex,
                hour = hour,
                temp = 10.0 + (hour % 12) - (hour / 12) * 5, // Temperature curve through the day
                weatherCode = weatherCode
            )
        }
    }
}