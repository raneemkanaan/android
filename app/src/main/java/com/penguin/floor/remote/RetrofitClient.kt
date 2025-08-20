
package com.penguin.floor.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    val instance: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://192.168.5.28/database/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}
