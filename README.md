# Meteora - Weather Forecast

Meteora is a multiplatform weather forecast application developed using Kotlin Multiplatform and Compose Multiplatform, running on both Android and iOS.

> **Note:** This project is still in active development.

## About the Project

This project is an attempt to recreate the functionality and design of Apple's Weather app. The application uses the OpenMeteo API to retrieve weather data and OpenStreetMap Nominatim for geocoding services, providing users with detailed information about current weather conditions and forecasts.

## Features

- Display current weather for selected locations
- Hourly forecast for the current day
- 10-day forecast
- Detailed weather information: temperature, feels-like temperature, wind speed and direction, visibility, UV index, sunrise and sunset times
- Location search
- Automatic current location detection

## Tech Stack

- **Kotlin Multiplatform** - for sharing code between platforms
- **Compose Multiplatform** - for cross-platform UI
- **Ktor** - for networking and API integration
- **SQLDelight** - for local data storage
- **Koin** - for dependency injection
- **Moko** - for geolocation and permissions handling
- **Kotlinx** - for coroutines and datetime operations
- **OpenMeteo** - weather data provider API
- **OpenStreetMap Nominatim** - for geocoding and location search

## Project Structure

- **/composeApp** - shared code for Android and iOS
  - **/commonMain** - main code shared across all platforms
  - **/androidMain** - Android-specific code
  - **/iosMain** - iOS-specific code
- **/iosApp** - entry point for the iOS application

## Requirements

- Android Studio Hedgehog or newer
- JDK 11 or newer
- For iOS: Xcode 15 or newer, macOS

Developed using [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)