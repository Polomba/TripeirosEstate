package com.example.homebalance.Interfaces

import com.example.homebalance.Classes.Payment
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PaymentI {
    @GET("listPagamentos")
    fun getPagamentos(): Call<List<Payment>>
    @GET("listPagamentos/{ID}")
    fun listPagamentoById(@Path("ID") id: Int): Call<List<Payment>>

}