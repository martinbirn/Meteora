package org.meteora.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class LocationInfoShort(
    val id: String,
    val displayName: String,
    val latitude: Double,
    val longitude: Double,
)
