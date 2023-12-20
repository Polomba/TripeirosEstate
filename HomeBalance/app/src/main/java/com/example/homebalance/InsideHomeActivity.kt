package com.example.homebalance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homebalance.Adapters.CustomAdapter

class InsideHomeActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CustomAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inside_home)

        recyclerView = findViewById(R.id.horizontalscroll)
        adapter = CustomAdapter()

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter

    }

    fun CreateTask ( V: View){
        val intent = Intent(this, AddTaskActivity::class.java)
        startActivity(intent)
    }
}