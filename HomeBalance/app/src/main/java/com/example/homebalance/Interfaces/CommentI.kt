package com.example.homebalance.Interfaces

import com.example.homebalance.Classes.Comments
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CommentI {
    @GET("CommentTask/{taskId}")
    fun listCommentsByTaskId(@Path("taskId") taskID: Int): Call<List<Comments>>

}