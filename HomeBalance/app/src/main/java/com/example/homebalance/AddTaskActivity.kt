package com.example.homebalance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.homebalance.R

class AddTaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)
    }
    fun openHome(v: View){
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }
    fun openProfile(v: View){
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }

}