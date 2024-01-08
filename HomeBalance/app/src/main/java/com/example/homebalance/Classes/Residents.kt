package com.example.homebalance.Classes

import com.google.gson.annotations.SerializedName

data class Residents (
    @SerializedName("houseId") val houseid: Int?,
    @SerializedName("userId") val userid: Int?,
)