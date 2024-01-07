package com.example.homebalance.Classes

import com.google.gson.annotations.SerializedName
import java.sql.Date
import java.sql.Timestamp

data class Task (
    @SerializedName("Id") val id: Int?,
    @SerializedName("Title") val title: String?,
    @SerializedName("Description") val description: String?,
    @SerializedName("Date") val date: Timestamp?,
    @SerializedName("State") val state: String?,
    @SerializedName("Photo") val photo: String?,
    @SerializedName("ProfilePicture") val profilepicture: String?,
    @SerializedName("HomeId") val homeid: Int?,
    @SerializedName("Home") val home: Home?,
    @SerializedName("UserId") val userid: Int?,
    @SerializedName("User") val user: User?,
)
