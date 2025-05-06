package org.meteora.data.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.meteora.domain.entity.LocationInfo

@Serializable
data class NominatimResponse(
    @SerialName("address") 
    val address: Address
) {
    fun toDomain(): LocationInfo {
        return LocationInfo(
            city = address.city,
            country = address.country,
            countryCode = address.countryCode
        )
    }
}

@Serializable
data class Address(
    @SerialName("city") 
    val city: String,
    
    @SerialName("country") 
    val country: String,
    
    @SerialName("country_code") 
    val countryCode: String
) 