package com.example.homebalance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }

    fun addHome(v:View){
        val intent = Intent(this, AddHomeActivity::class.java)
        startActivity(intent)
    }

    fun openHome(v:View){
        val intent = Intent(this, InsideHomeActivity::class.java)
        startActivity(intent)
    }
}