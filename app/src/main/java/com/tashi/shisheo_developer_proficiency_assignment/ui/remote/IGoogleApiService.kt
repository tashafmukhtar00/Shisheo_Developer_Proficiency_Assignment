package com.tashi.shisheo_developer_proficiency_assignment.ui.remote

import com.tashi.shisheo_developer_proficiency_assignment.ui.model.MyPlaces
import retrofit2.http.GET
import retrofit2.http.Url

interface IGoogleApiService {

    @GET
    fun getNearbyPlaces(@Url url: String): retrofit2.Call<MyPlaces>
}