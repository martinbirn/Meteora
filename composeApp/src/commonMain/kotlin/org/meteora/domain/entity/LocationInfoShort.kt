package org.meteora.domain.entity

import androidx.navigation.NavType
import androidx.savedstate.SavedState
import androidx.savedstate.read
import androidx.savedstate.write
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class LocationInfoShort(
    val key: String,
    val displayName: String,
    val latitude: Double,
    val longitude: Double,
)

val LocationInfoShortNavType: NavType<LocationInfoShort>
    get() = object : NavType<LocationInfoShort>(
        isNullableAllowed = true
    ) {
        override fun put(bundle: SavedState, key: String, value: LocationInfoShort) = bundle.write {
            putString(key, serializeAsValue(value))
        }

        override fun get(bundle: SavedState, key: String) = bundle.read {
            parseValue(getString(key))
        }

        override fun parseValue(value: String) = Json.decodeFromString<LocationInfoShort>(value)

        override fun serializeAsValue(value: LocationInfoShort) = Json.encodeToString(value)
    }
