package com.example.jelajah3.model

import com.google.gson.annotations.SerializedName

data class Place(
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("category") val category: String,
    @SerializedName("photoUrl") val photoUrl: String,
    @SerializedName("rating") val rating: String,
    @SerializedName("location") val location: Location
)

data class Location(
    @SerializedName("latitude") val latitude: Double,
    @SerializedName("longitude") val longitude: Double
)
