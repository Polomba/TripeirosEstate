package com.example.homebalance.Classes

import com.google.gson.annotations.SerializedName

data class Auth (
    @SerializedName("Id") val id: Int?,

    @SerializedName("Email") val email : String?,
    @SerializedName("Password") val password : String?,
)