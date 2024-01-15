package com.example.homebalance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.homebalance.R

class AddTaskActivity : AppCompatActivity() {

    private lateinit var tv_TaskName: TextView
    private lateinit var dtpicker_end: DatePicker // Supondo que você tenha um DatePicker no layout
    private lateinit var imageView: ImageView
    private lateinit var et_ToDo: EditText
    private lateinit var et_addcomment: EditText
    private lateinit var tv_comment: TextView

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