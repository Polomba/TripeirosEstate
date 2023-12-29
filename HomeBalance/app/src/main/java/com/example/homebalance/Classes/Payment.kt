package com.example.homebalance.Classes

import com.google.gson.annotations.SerializedName
import java.sql.Date

class Payment (
    @SerializedName("Id") val id: Int?,
    @SerializedName("Value") val value: Float?,
    @SerializedName("EmissionDate") val emissiondate: Date?,
    @SerializedName("PaymentDate") val payment: Payment?,
    @SerializedName("Description") val description: Payment?,
    @SerializedName("UserId") val userid: Int?,
    @SerializedName("User") val user: User?,
)