package com.example.homebalance.Classes

import com.google.gson.annotations.SerializedName

data class Review (
    @SerializedName("Id") val id: Int?,
    @SerializedName("Rating") val rating: Float?,
    @SerializedName("Comment") val comment: String?,
    @SerializedName("TaskId") val taskid: Int?,
)