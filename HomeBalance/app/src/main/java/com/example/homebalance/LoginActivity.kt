package com.example.homebalance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun goBack(v:View) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    fun doLoginLP(V:View){
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }


}