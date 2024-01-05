package com.example.homebalance.Interfaces
import com.example.homebalance.Classes.Auth
import com.example.homebalance.Classes.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthI {
    @POST("authLogin")
    fun authLogin(
        @Body userData: User
    ): Call<Any>

    @POST("authRegister")
    fun authRegister(
        @Body userData : User
    ): Call<Any>
}