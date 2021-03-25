package com.tashi.shisheo_developer_proficiency_assignment.ui.common

import com.tashi.shisheo_developer_proficiency_assignment.ui.remote.IGoogleApiService
import com.tashi.shisheo_developer_proficiency_assignment.ui.remote.RetrofitClient

object Common {

    private val GOOGLE_API_URL = "https://maps.googleapis.com/"

    val googleApiService: IGoogleApiService
        get() = RetrofitClient.getClient(GOOGLE_API_URL).create(IGoogleApiService::class.java)
}