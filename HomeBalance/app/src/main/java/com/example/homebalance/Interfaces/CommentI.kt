package com.example.homebalance.Interfaces

import com.example.homebalance.Classes.Comments
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CommentI {
    @GET("listComments")
    fun listComments(): Call<List<Comments>>
    @GET("listPagamentos/{ID}")
    fun listPagamentoById(@Path("ID") id: Int): Call<List<Comments>>
}