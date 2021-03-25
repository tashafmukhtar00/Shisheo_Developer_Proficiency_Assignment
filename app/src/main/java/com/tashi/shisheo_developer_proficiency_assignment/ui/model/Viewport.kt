package com.tashi.shisheo_developer_proficiency_assignment.ui.model

import com.google.gson.annotations.SerializedName

class Viewport {
    @SerializedName("northeast")
    var northeast: Northeast? = null

    @SerializedName("southwest")
    var southwest: Southwest? = null

}
