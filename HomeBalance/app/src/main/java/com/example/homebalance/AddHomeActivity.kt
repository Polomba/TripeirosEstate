package com.example.homebalance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.homebalance.Classes.GlobalVariables
import com.example.homebalance.Classes.Home
import com.example.homebalance.Interfaces.HomeI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AddHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_home)

        val homenameEditText : EditText = findViewById<EditText>(R.id.et_housename)
        val homeaddressEditText : EditText = findViewById<EditText>(R.id.et_houseadress)

        val homename = homenameEditText.text.toString()
        val homeaddress = homeaddressEditText.text.toString()

        val retrofit = Retrofit.Builder()
            .baseUrl(GlobalVariables.HOMEBALANCE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val home = Home(
            null,
            homename,
            homeaddress
        )

        val service = retrofit.create(HomeI::class.java)

        val call = service.createHouse(home)

        call.enqueue(object : Callback<Any>{
            override fun onResponse(call: Call<Any>, response: Response<Any>) {

            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
            }

        })
    }

    fun goInviteResident(v: View){
        val intent = Intent(this@AddHomeActivity, AddResidentActivity::class.java)
        startActivity(intent)
    }
}