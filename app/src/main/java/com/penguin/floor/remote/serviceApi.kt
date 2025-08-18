package com.penguin.floor.remote

import com.penguin.floor.Floor
import retrofit2.Call
import retrofit2.http.GET

interface ServiceApi {
    @GET("api/Floors")
    fun getFloors(): Call<List<Floor>>
}
