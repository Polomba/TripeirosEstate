package com.example.homebalance.Interfaces

import com.example.homebalance.Classes.Home
import com.example.homebalance.Classes.HomeResponse
import com.example.homebalance.Classes.Residents
import com.example.homebalance.Classes.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ResidentI {
    @GET("Resident/{HouseId}")
    fun getResidentsByHouseId(@Path("HouseId") houseId: Int): Call<List<User>>

    @GET("Residents/{UserId}")
    fun getHouseByUserId(@Path("UserId") userId: Int): Call<List<Home>>

    @POST("Resident")
    fun addResident(
        @Body residentData : Residents
    ): Call<HomeResponse>
}
