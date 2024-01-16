package com.example.homebalance.Interfaces

import com.example.homebalance.Classes.HomeResponse
import com.example.homebalance.Classes.Task
import com.example.homebalance.Classes.TaskIdResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface TaskI {

    @GET("Tarefa/{homeid}")
    fun listTarefaById(@Path("homeid") id: Int): Call<List<Task>>

    @GET("Tarefas/{taskid}")
    fun listTarefaByTaskId(@Path("taskid") id: Int): Call<List<Task>>

    @POST("Tarefa")
    fun addTarefa(
        @Header("Authorization") token: String?,
        @Body taskData: Task
    ): Call<List<TaskIdResponse>>

}