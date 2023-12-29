package com.example.homebalance.Classes

import com.google.gson.annotations.SerializedName

data class Comments (
    @SerializedName("Id") val id: Int?,
    @SerializedName("Comment") val comment: String?,
    @SerializedName("TaskId") val taskid: Int?,
    @SerializedName("Task") val task: Task?,
    @SerializedName("UserId") val userid: Int?,
    @SerializedName("User") val user: User?,
)