package org.meteora.domain.entity

import androidx.navigation.NavType
import androidx.savedstate.SavedState
import androidx.savedstate.read
import androidx.savedstate.write
import dev.icerock.moko.geo.LatLng
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class LatLong(
    val latitude: Double,
    val longitude: Double
) {
    fun toLatLng() = LatLng(latitude, longitude)
}

val LatLongNavType = object : NavType<LatLong>(
    isNullableAllowed = true
) {
    override fun put(bundle: SavedState, key: String, value: LatLong) = bundle.write {
        putString(key, serializeAsValue(value))
    }

    override fun get(bundle: SavedState, key: String) = bundle.read {
        parseValue(getString(key))
    }

    override fun parseValue(value: String) = Json.decodeFromString<LatLong>(value)

    override fun serializeAsValue(value: LatLong) = Json.encodeToString(value)
}
