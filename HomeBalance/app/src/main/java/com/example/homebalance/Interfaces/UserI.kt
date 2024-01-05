package com.example.homebalance.Interfaces

import com.example.homebalance.Classes.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface UserI {
    @GET("Utilizador/{Email}")
    fun getUserByEmail(@Path("Email") email: String): Call<List<User>>
}