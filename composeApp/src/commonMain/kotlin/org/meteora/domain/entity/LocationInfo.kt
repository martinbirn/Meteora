package org.meteora.domain.entity

data class LocationInfo(
    val latitude: Double,
    val longitude: Double,
    val locality: String?, // city, town, village, administrative, etc.
    val country: String,
    val countryCode: String,
    val displayName: String
)
