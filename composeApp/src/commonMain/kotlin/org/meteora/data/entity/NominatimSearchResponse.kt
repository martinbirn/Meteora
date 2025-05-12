package org.meteora.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.jsonPrimitive
import org.meteora.domain.entity.LocationInfo

@Serializable
data class NominatimSearchResponse(

    @SerialName("lat")
    val latitude: String,

    @SerialName("lon")
    val longitude: String,

    @SerialName("name")
    val name: String? = null,

    @SerialName("display_name")
    val displayName: String,

    @SerialName("addresstype")
    val addressType: String,

    @SerialName("address")
    val address: JsonObject,
) {
    fun toDomain(): LocationInfo {
        return LocationInfo(
            latitude = latitude.toDouble(),
            longitude = longitude.toDouble(),
            locality = address[addressType]?.jsonPrimitive?.content,
            country = address["country"]?.jsonPrimitive?.content,
            countryCode = address["countryCode"]?.jsonPrimitive?.content,
            displayName = displayName
        )
    }
}

@Serializable
data class SearchAddress(

    @SerialName("city")
    val city: String,

    @SerialName("state")
    val state: String?,

    @SerialName("country_code")
    val countryCode: String,

    @SerialName("country")
    val country: String,

    @SerialName("suburb")
    val suburb: String?,

    @SerialName("postcode")
    val postcode: String?
)