package com.example.homebalance

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.homebalance.Classes.GlobalVariables
import com.example.homebalance.Classes.Home
import com.example.homebalance.Classes.Residents
import com.example.homebalance.Interfaces.HomeI
import com.example.homebalance.Interfaces.ResidentI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AddHomeActivity : AppCompatActivity() {
    private lateinit var homenameEditText: EditText
    private lateinit var homeaddressEditText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_home)

        homenameEditText = findViewById(R.id.et_housename)
        homeaddressEditText = findViewById(R.id.et_houseadress)
    }

    fun AddHouse(v: View) {
        val homename = homenameEditText.text.toString()
        val homeaddress = homeaddressEditText.text.toString()

        val retrofit = Retrofit.Builder()
            .baseUrl(GlobalVariables.HOMEBALANCE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val home = Home(null, homename, homeaddress)
        val service = retrofit.create(HomeI::class.java)
        val call = service.createHouse(home)

        call.enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if (response.isSuccessful) {

                } else {
                    Log.d("Home", "Resposta não bem-sucedida: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                getHouseIdByName(homename)
            }
        })
    }

    fun AddResidentToHouse(homeId: Int, userId: Int?) {
        val retrofit = Retrofit.Builder()
            .baseUrl(GlobalVariables.HOMEBALANCE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val residentData = Residents(homeId, userId)

        val service = retrofit.create(ResidentI::class.java)
        val call = service.addResident(residentData)

        call.enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if (response.isSuccessful) {
                    setResult(RESULT_OK)
                    finish()
                } else {
                    Log.d("Resident", "Resposta não bem-sucedida: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                Log.d("Resident", "Erro na chamada: ${t.message}")
            }
        })
    }

    fun getHouseIdByName(housename: String) {
        val retrofit = Retrofit.Builder()
            .baseUrl(GlobalVariables.HOMEBALANCE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(HomeI::class.java)
        val call = service.getHouseId(housename)

        call.enqueue(object : Callback<Int> {
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                if (response.isSuccessful) {
                    val homeId = response.body()
                    homeId?.let {
                        val userId = intent.extras?.getInt("user_id")
                        AddResidentToHouse(it, userId)
                    }
                }
            }

            override fun onFailure(call: Call<Int>, t: Throwable) {
                Log.d("Home", "Erro ao obter ID da casa: ${t.message}")
            }
        })
    }
}
