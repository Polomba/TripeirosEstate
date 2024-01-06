package com.example.homebalance.Interfaces

import com.example.homebalance.Classes.Home
import com.example.homebalance.Classes.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface HomeI {
    @POST("House")
    fun createHouse(
        @Body homeData : Home
    ): Call<Any>

    @GET("House/{houseName}")
    fun getHouseId(@Path("houseName") name: String): Call<Int>
}