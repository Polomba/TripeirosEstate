package com.example.homebalance.Classes

import com.google.gson.annotations.SerializedName

data class User (
    @SerializedName("Id") val id: Int?,
    @SerializedName("Name") val name: String?,
    @SerializedName("Email") val email: String?,
    @SerializedName("Password") val password: String?,
    @SerializedName("Roles") val roles: String?,
    @SerializedName("Token") val token: String?,
    @SerializedName("ProfilePicture") val ProfilePicture: String?,
)