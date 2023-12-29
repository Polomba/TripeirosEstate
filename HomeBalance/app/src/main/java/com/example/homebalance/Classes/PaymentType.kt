package com.example.homebalance.Classes

import com.google.gson.annotations.SerializedName

data class PaymentType (
    @SerializedName("Id") val id: Int?,
    @SerializedName("Type") val name: String?,
    @SerializedName("PaymentId") val PaymentId: Int?,
    @SerializedName("Payment") val Payment: Payment?,
)
