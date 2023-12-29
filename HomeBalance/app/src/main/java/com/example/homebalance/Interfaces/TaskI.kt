package com.example.homebalance.Interfaces

import com.example.homebalance.Classes.Task
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface TaskI {
    @GET("listTarefas")
    fun getTarefas(): Call<List<Task>>
    @GET("listTarefa/{ID}")
    fun listTarefaById(@Path("ID") id: Int): Call<List<Task>>

}