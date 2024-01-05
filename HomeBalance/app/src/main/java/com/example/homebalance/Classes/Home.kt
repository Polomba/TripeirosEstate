package com.example.homebalance.Classes

import com.google.gson.annotations.SerializedName

data class Home (
    @SerializedName("Homeid") val id: Int?,
    @SerializedName("Name") val name: String?,
    @SerializedName("Adress") val adress: String?,
)