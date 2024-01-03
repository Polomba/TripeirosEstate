package com.example.homebalance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import retrofit2.Retrofit
import com.example.homebalance.Classes.GlobalVariables.HOMEBALANCE_URL
import com.example.homebalance.Classes.User
import com.example.homebalance.Interfaces.AuthI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun goBack(v:View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun doLoginLP(view: View) {
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
                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this@LoginActivity, "Email ou Password Errado", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                print("")
                Toast.makeText(this@LoginActivity, "Erro na requisição: " + t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

}