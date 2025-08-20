package com.penguin.floor.remote

import com.penguin.floor.Floor
import retrofit2.Call
import retrofit2.http.*

interface ServiceApi {

    @GET("floors.php")
    fun getFloors(): Call<List<Floor>>

    @POST("saveFloor.php")
    fun saveFloor(
        @Body floor: Floor
    ): Call<Floor>

    @PUT("updateFloor.php")
    fun updateFloor(
        @Query("id") id: Int,
        @Body floor: Floor
    ): Call<Floor>

    @DELETE("deleteFloor.php")
    fun deleteFloor(
        @Query("id") id: Int?
    ): Call<Void>
}
