package com.example.homebalance.Interfaces

import com.example.homebalance.Classes.Home
import com.example.homebalance.Classes.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ResidentI {
    @GET("Resident/{HouseId}")
    fun getResidentsByHouseId(@Path("HouseId") houseId: Int): Call<List<User>>

    @GET("ResidentS/{UserId}")
    fun getHouseByUserId(@Path("UserId") userId: Int): Call<List<Home>>
}
