package org.meteora.domain.entity

import androidx.navigation.NavType
import androidx.savedstate.SavedState
import androidx.savedstate.read
import androidx.savedstate.write
import dev.icerock.moko.geo.LatLng
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class LocationInfo(
    val id: String,
    val latitude: Double,
    val longitude: Double,
    val locality: String?, // city, town, village, administrative, etc.
    val country: String,
    val countryCode: String,
    val displayName: String
) {
    val latLng: LatLng
        get() = LatLng(latitude, longitude)

    fun toShortInfo(): LocationInfoShort {
        return LocationInfoShort(
            id = id,
            displayName = displayName,
            latitude = latitude,
            longitude = longitude,
        )
    }
}

val LocationInfoNavType: NavType<LocationInfo>
    get() = object : NavType<LocationInfo>(
        isNullableAllowed = true
    ) {
        override fun put(bundle: SavedState, key: String, value: LocationInfo) = bundle.write {
            putString(key, serializeAsValue(value))
        }

        override fun get(bundle: SavedState, key: String) = bundle.read {
            parseValue(getString(key))
        }

        override fun parseValue(value: String) = Json.decodeFromString<LocationInfo>(value)

        override fun serializeAsValue(value: LocationInfo) = Json.encodeToString(value)
    }
