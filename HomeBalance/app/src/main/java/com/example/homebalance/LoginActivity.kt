package com.example.homebalance

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import retrofit2.Retrofit
import com.example.homebalance.Classes.GlobalVariables.HOMEBALANCE_URL
import com.example.homebalance.Classes.User
import com.example.homebalance.Interfaces.AuthI
import com.example.homebalance.Interfaces.UserI
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun goBack(v: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun doLoginLP(v: View) {
        val emailEditText: EditText = findViewById(R.id.et_email)
        val passwordEditText: EditText = findViewById(R.id.et_password)

        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        val retrofit = Retrofit.Builder()
            .baseUrl(HOMEBALANCE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val user = User(
            null,
            null,
            email,
            password,
            null,
            null,
            null
        )

        val service = retrofit.create(AuthI::class.java)
        val call = service.authLogin(user)

        call.enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if (response.isSuccessful) {
                    val servicegetUser = retrofit.create(UserI::class.java)
                    val callgetUser = servicegetUser.getUserByEmail(email)
                    getUserInfo(callgetUser)
                } else {
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                Toast.makeText(
                    this@LoginActivity,
                    "Erro na requisição: " + t.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    fun getUserInfo(call: Call<List<User>>) {
        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val userList: List<User>? = response.body()
                    if (userList != null && userList.isNotEmpty()) {
                        val user: User = userList[0]
                        val userId = user.id ?: 0

                        val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                        intent.putExtra("user_id", userId)
                        startActivity(intent)
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