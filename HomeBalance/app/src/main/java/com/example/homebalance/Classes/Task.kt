package com.example.homebalance.Classes

import com.google.gson.annotations.SerializedName
import java.sql.Date
import java.sql.Timestamp

data class Task (
    @SerializedName("Id") val id: Int?,
    @SerializedName("Title") val title: String?,
    @SerializedName("Description") val description: String?,
    @SerializedName("Data") val date: Timestamp?,
    @SerializedName("State") val state: String?,
    @SerializedName("Photo") val photo: String?,
    @SerializedName("HomeId") val homeid: Int?,
    @SerializedName("UserId") val userid: Int?,
)
