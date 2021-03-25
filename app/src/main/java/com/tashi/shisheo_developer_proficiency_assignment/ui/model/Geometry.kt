package com.tashi.shisheo_developer_proficiency_assignment.ui.model

import com.google.gson.annotations.SerializedName

data class Geometry(
    @SerializedName("location") val location: Location,
    @SerializedName("viewport") val viewport: Viewport
)
