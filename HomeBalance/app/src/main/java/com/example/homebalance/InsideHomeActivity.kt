package com.example.homebalance

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView

class InsideHomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inside_home)

        val listView = findViewById<ListView>(R.id.listView)

        val itemList = ArrayList<Any>() // Storing a list of Any type here

        // Hardcoded data for testing the ListView
        itemList.add(R.drawable.icons8_back_50px_1)
        itemList.add("Text 1")
        itemList.add(R.drawable.icons8_back_50px_1)
        itemList.add("Text 2")
        // Add more items as needed...

        val adapter = CustomAdapter(this)
        listView.adapter = adapter

        // Add the hardcoded item list to the adapter
        adapter.addAll(itemList)
    }
}