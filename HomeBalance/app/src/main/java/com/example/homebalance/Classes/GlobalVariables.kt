package com.example.homebalance.Classes

import android.content.Context
import android.content.SharedPreferences
import com.example.homebalance.R

object GlobalVariables {
    const val HOMEBALANCE_URL = "http://192.168.1.72:3000/api/"

    fun getSharedPreferencesContext(context: Context): SharedPreferences {
        return context.getSharedPreferences(context.resources.getString(R.string.app_name), Context.MODE_PRIVATE)
    }
}