package com.example.homebalance

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.homebalance.Classes.GlobalVariables
import com.example.homebalance.Classes.Home
import com.example.homebalance.Classes.HomeResponse
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

    fun openHome(v: View) {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    fun openProfile(v: View) {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }

    fun AddHouse(v: View) {
        val homename = homenameEditText.text.toString()
        val homeaddress = homeaddressEditText.text.toString()
        val userId = intent.extras?.getInt("user_id")

        val retrofit = Retrofit.Builder()
            .baseUrl(GlobalVariables.HOMEBALANCE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val home = Home(null, homename, homeaddress, userId)
        val sp = GlobalVariables.getSharedPreferencesContext(this@AddHomeActivity)
        val token = sp.getString("authToken", null)
        val service = retrofit.create(HomeI::class.java)
        val call = service.createHouse(token ,home)

        call.enqueue(object : Callback<List<HomeResponse>> {
            override fun onResponse(call: Call<List<HomeResponse>>, response: Response<List<HomeResponse>>) {
                if (response.isSuccessful) {
                    val homes = response.body()
                    if (!homes.isNullOrEmpty()) {
                        val firstHomeId = homes[0].id
                        val userId = intent.extras?.getInt("user_id")
                        AddResidentToHouse(firstHomeId, userId)
                    } else {
                        Log.d("Home", "Lista de casas vazia")
                    }
                } else {
                    Log.d("Home", "Resposta não bem-sucedida: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<HomeResponse>>, t: Throwable) {
                Log.e("Home", "Falha na requisição: ${t.message}")
            }
        })

    }

    fun AddResidentToHouse(houseId: Int?, userId: Int?) {
        val retrofit = Retrofit.Builder()
            .baseUrl(GlobalVariables.HOMEBALANCE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val residentData = Residents(houseId, userId)

        val service = retrofit.create(ResidentI::class.java)
        val call = service.addResident(residentData)

        call.enqueue(object : Callback<HomeResponse> {
            override fun onResponse(call: Call<HomeResponse>, response: Response<HomeResponse>) {

            }

            override fun onFailure(call: Call<HomeResponse>, t: Throwable) {
                setResult(RESULT_OK)
                finish()
            }
        })
    }

}
