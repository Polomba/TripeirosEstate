package com.example.homebalance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import com.example.homebalance.Classes.GlobalVariables
import com.example.homebalance.Classes.Home
import com.example.homebalance.Classes.User
import com.example.homebalance.Interfaces.HomeI
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

    fun openHome(v:View){
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }
    fun openProfile(v:View){
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }

    fun doAddResident(v: View){
        val emailEditText : EditText = findViewById<EditText>(R.id.emailet)

        val email = emailEditText.text.toString()


        val retrofit = Retrofit.Builder()
            .baseUrl(GlobalVariables.HOMEBALANCE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val servicegetUser = retrofit.create(UserI::class.java)
        val callgetUser = servicegetUser.getUserByEmail(email)

    }

    fun getUserInfo(call: Call<List<User>>) {
        call.enqueue(object : Callback<List<User>>{
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if(response.isSuccessful){
                    val userList: List<User>? = response.body()
                    if (userList != null && userList.isNotEmpty()) {
                        val user: User = userList[0]
                        val userId = user.id ?: 0

                    } else {
                    }
                } else {
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Log.e("Teste", "Falha na requisição: ${t.message}")
                // Trate a falha na requisição, se necessário
            }
        })
    }

}