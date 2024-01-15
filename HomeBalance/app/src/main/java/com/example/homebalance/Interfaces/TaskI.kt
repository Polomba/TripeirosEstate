package com.example.homebalance.Interfaces

import com.example.homebalance.Classes.Task
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface TaskI {
    @GET("Tarefa")
    fun getTarefas(): Call<List<Task>>
    @GET("Tarefa/{homeid}")
    fun listTarefaById(@Path("homeid") id: Int): Call<List<Task>>

    @GET("Tarefas/{taskid}")
    fun listTarefaByTaskId(@Path("taskid") id: Int): Call<List<Task>>

}