package com.example.homebalance.Interfaces


import com.example.homebalance.Classes.Review
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface ReviewI {
    @POST("ReviewTask/{taskId}")
    fun addReview(
        @Path("taskId") taskId: Int,
        @Body review: Review
    ): Call<Any>
}