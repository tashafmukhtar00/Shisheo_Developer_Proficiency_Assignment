package com.tashi.shisheo_developer_proficiency_assignment.ui.model

import com.google.gson.annotations.SerializedName

data class Results(
    @SerializedName("business_status") val business_status: String,
    @SerializedName("geometry") val geometry: Geometry,
    @SerializedName("icon") val icon: String,
    @SerializedName("name") val name: String,
    @SerializedName("photos") val photos: List<Photos>,
    @SerializedName("place_id") val place_id: String,
    @SerializedName("plus_code") val plus_code: Plus_code,
    @SerializedName("price_level") val price_level: Int,
    @SerializedName("rating") val rating: Double,
    @SerializedName("reference") val reference: String,
    @SerializedName("scope") val scope: String,
    @SerializedName("types") val types: List<String>,
    @SerializedName("user_ratings_total") val user_ratings_total: Int,
    @SerializedName("vicinity") val vicinity: String
)
