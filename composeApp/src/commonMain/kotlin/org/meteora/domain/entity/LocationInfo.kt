package org.meteora.domain.entity

import dev.icerock.moko.geo.LatLng
import kotlinx.serialization.Serializable

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
