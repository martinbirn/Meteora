package org.meteora.utils

import kotlinx.serialization.json.Json

val MeteoraJson by lazy {
    Json {
        prettyPrint = true
        ignoreUnknownKeys = true
        isLenient = true
        explicitNulls = false
    }
}