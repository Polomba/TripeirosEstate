package com.example.homebalance.Interfaces

import com.example.homebalance.Classes.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface UserI {
    @GET("Utilizador/{Email}")
    fun getUserByEmail(@Path("Email") email: String): Call<List<User>>

    @GET("Utilizadores/{Id}")
    fun getUserById(@Path("Id") id: Int): Call<List<User>>

    @PUT("Utilizador/{Id}")
    fun updateUser(@Path("Id")id : Int, @Body userData : User): Call<List<User>>
}