package org.meteora.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.meteora.domain.entity.LocationInfo

@Serializable
data class DbLocation(
    @SerialName("id")
    val id: String,
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("longitude")
    val longitude: Double,
    @SerialName("locality")
    val locality: String?,
    @SerialName("country")
    val country: String,
    @SerialName("countryCode")
    val countryCode: String,
    @SerialName("displayName")
    val displayName: String
) {

    fun toDomain(): LocationInfo {
        return LocationInfo(
            id = id,
            latitude = latitude,
            longitude = longitude,
            locality = locality,
            country = country,
            countryCode = countryCode,
            displayName = displayName,
        )
    }

    companion object {
        fun fromDomain(location: LocationInfo): DbLocation {
            return DbLocation(
                id = location.id,
                latitude = location.latitude,
                longitude = location.longitude,
                locality = location.locality,
                country = location.country,
                countryCode = location.countryCode,
                displayName = location.displayName,
            )
        }
    }
}
