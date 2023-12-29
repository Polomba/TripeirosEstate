package com.example.homebalance.Classes

import com.google.gson.annotations.SerializedName

data class Residents (
    @SerializedName("HomeId") val homeid: Int?,
    @SerializedName("Home") val home: Home?,
    @SerializedName("UserId") val userid: Int?,
    @SerializedName("User") val user: User?,
)