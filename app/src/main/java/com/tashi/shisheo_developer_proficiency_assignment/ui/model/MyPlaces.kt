package com.tashi.shisheo_developer_proficiency_assignment.ui.model

import com.google.gson.annotations.SerializedName

data class MyPlaces(
    @SerializedName("html_attributions") val html_attributions: List<String>,
    @SerializedName("results") val results: List<Results>,
    @SerializedName("status") val status: String
)