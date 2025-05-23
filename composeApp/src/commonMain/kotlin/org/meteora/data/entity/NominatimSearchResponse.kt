package org.meteora.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.meteora.domain.entity.LocationInfo
import kotlin.uuid.Uuid

@Serializable
data class NominatimSearchResponse(

    @SerialName("lat")
    val latitude: Double,

    @SerialName("lon")
    val longitude: Double,

    @SerialName("name")
    val name: String? = null,

    @SerialName("display_name")
    val displayName: String,

    @SerialName("addresstype")
    val addressType: String,

    @SerialName("address")
    val address: SearchAddress,
) {
    fun toDomain(): LocationInfo {
        return LocationInfo(
            id = Uuid.random().toString(),
            latitude = latitude,
            longitude = longitude,
            locality = address.anyLocality,
            country = address.country,
            countryCode = address.countryCode,
            displayName = displayName
        )
    }
}

@Serializable
data class SearchAddress(

    @SerialName("city")
    val city: String?,

    @SerialName("town")
    val town: String?,

    @SerialName("village")
    val village: String?,

    @SerialName("hamlet")
    val hamlet: String?,

    @SerialName("municipality")
    val municipality: String?,

    @SerialName("county")
    val county: String?,

    @SerialName("state")
    val state: String?,

    @SerialName("administrative")
    val administrative: String?,

    @SerialName("suburb")
    val suburb: String?,

    @SerialName("neighbourhood")
    val neighbourhood: String?,

    @SerialName("isolated_dwelling")
    val isolatedDwelling: String?,

    @SerialName("quarter")
    val quarter: String?,

    @SerialName("locality")
    val locality: String?,

    @SerialName("allotments")
    val allotments: String?,

    @SerialName("road")
    val road: String?,

    @SerialName("country")
    val country: String,

    @SerialName("country_code")
    val countryCode: String,

    @SerialName("postcode")
    val postcode: String?
) {
    val anyLocality: String?
        get() = city
            ?: town
            ?: village
            ?: hamlet
            ?: municipality
            ?: suburb
            ?: neighbourhood
            ?: isolatedDwelling
            ?: quarter
            ?: locality
            ?: allotments
            ?: road
            ?: administrative
            ?: county
            ?: state
}
