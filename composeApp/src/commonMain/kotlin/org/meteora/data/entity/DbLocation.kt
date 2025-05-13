package org.meteora.data.entity

import kotlinx.serialization.Serializable
import org.meteora.domain.entity.LocationInfo

@Serializable
data class DbLocation(
    val latitude: Double,
    val longitude: Double,
    val locality: String?,
    val country: String,
    val countryCode: String,
    val displayName: String
) {
    companion object {
        fun fromDomain(location: LocationInfo): DbLocation {
            return DbLocation(
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
