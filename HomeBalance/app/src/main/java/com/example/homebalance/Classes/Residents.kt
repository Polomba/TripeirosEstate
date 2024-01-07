package com.example.homebalance.Classes

import com.google.gson.annotations.SerializedName

data class Residents (
    @SerializedName("HouseId") val homeid: Int?,
    @SerializedName("UserId") val userid: Int?,
)