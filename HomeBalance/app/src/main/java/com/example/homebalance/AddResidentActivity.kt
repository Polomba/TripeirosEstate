package com.example.homebalance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import com.example.homebalance.Classes.GlobalVariables
import com.example.homebalance.Classes.Home
import com.example.homebalance.Classes.HomeResponse
import com.example.homebalance.Classes.Residents
import com.example.homebalance.Classes.User
import com.example.homebalance.Interfaces.HomeI
import com.example.homebalance.Interfaces.ResidentI
import com.example.homebalance.Interfaces.UserI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AddResidentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_resident)
    }

    fun doAddResident(v: View) {
        getUserInfo()
    }

    fun AddResidentToHouse(houseId: Int?, userId: Int?) {
        Log.d("AddResidentToHouse", "House ID: $houseId, User ID: $userId")

        val retrofit = Retrofit.Builder()
            .baseUrl(GlobalVariables.HOMEBALANCE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val residentData = Residents(houseId, userId)

        val service = retrofit.create(ResidentI::class.java)
        val call = service.addResident(residentData)

        call.enqueue(object : Callback<HomeResponse> {
            override fun onResponse(call: Call<HomeResponse>, response: Response<HomeResponse>) {
                setResult(RESULT_OK)
                finish()
            }

            override fun onFailure(call: Call<HomeResponse>, t: Throwable) {
            }
        })
    }

    fun getUserInfo() {
        val emailEditText: EditText = findViewById<EditText>(R.id.emailet)
        val houseId = intent.extras?.getInt("house_id")
        val email = emailEditText.text.toString()
        val retrofit = Retrofit.Builder()
            .baseUrl(GlobalVariables.HOMEBALANCE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val servicegetUser = retrofit.create(UserI::class.java)
        val callgetUser = servicegetUser.getUserByEmail(email)

        callgetUser.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val userList: List<User>? = response.body()
                    if (userList != null && userList.isNotEmpty()) {
                        val user: User = userList[0]
                        val userId = user.id ?: 0
                        AddResidentToHouse(houseId, userId)
                    } else {
                        Log.d("getUserInfo", "User list is empty")
                    }
                } else {
                    Log.d("getUserInfo", "Response is not successful")
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.e("Teste", "Falha na requisição: ${t.message}")
            }
        })

    }

}