package com.example.homebalance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.homebalance.Classes.GlobalVariables
import com.example.homebalance.Classes.User
import com.example.homebalance.Interfaces.AuthI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    fun goBack(v: View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun Register(v: View) {
        val emailEditText: EditText = findViewById(R.id.et_email)
        val passwordEditText: EditText = findViewById(R.id.et_password)
        val nomeEditText: EditText = findViewById(R.id.et_name)

        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        val name = nomeEditText.text.toString()

        val retrofit = Retrofit.Builder()
            .baseUrl(GlobalVariables.HOMEBALANCE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val user = User(
            null,
            name,
            email,
            password,
            null,
            null,
            null
        )

        val service = retrofit.create(AuthI::class.java)
        val call = service.authRegister(user)

        call.enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if (response.isSuccessful) {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Utilizador criado com sucesso!",
                        Toast.LENGTH_SHORT
                    ).show()
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(
                        this@RegisterActivity,
                        "Email já em utilização ou password impossivel!",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                print("")
                Toast.makeText(
                    this@RegisterActivity,
                    "Erro na requisição: " + t.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}