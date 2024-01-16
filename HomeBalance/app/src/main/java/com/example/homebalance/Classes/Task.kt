package com.example.homebalance.Classes

import com.google.gson.annotations.SerializedName

data class Task(
    @SerializedName("Id") val id: Int?,
    @SerializedName("Title") val title: String?,
    @SerializedName("Description") val description: String?,
    @SerializedName("Data") val date: String,
    @SerializedName("State") val state: String?,
    @SerializedName("Photo") val photo: String?,
    @SerializedName("Homeid") val homeid: Int?,
    @SerializedName("UserId") val userid: Int?,
)
