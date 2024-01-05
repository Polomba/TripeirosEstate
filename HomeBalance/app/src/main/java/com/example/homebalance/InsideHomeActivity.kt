package com.example.homebalance

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homebalance.Adapters.HorizontalListViewAdapter
import com.example.homebalance.Classes.GlobalVariables.HOMEBALANCE_URL
import com.example.homebalance.Classes.User
import com.example.homebalance.Interfaces.ResidentI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class InsideHomeActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HorizontalListViewAdapter

    private val retrofit = Retrofit.Builder()
        .baseUrl(HOMEBALANCE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val residentService = retrofit.create(ResidentI::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inside_home)

        recyclerView = findViewById(R.id.horizontalscroll)
        adapter = HorizontalListViewAdapter()

        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter


        val houseId = intent.extras?.getInt("home_id")
        if (houseId != null) {
            getResidentsByHouseId(houseId)
        }
    }

    private fun getResidentsByHouseId(houseId: Int) {
        val call = residentService.getResidentsByHouseId(houseId)
        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val residents = response.body()
                    residents?.let {
                        adapter.setResidents(it)
                    }
                } else {
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                // Tratar falha na requisição
            }
        })
    }

    fun CreateTask(V: View) {
        val intent = Intent(this@InsideHomeActivity, AddTaskActivity::class.java)
        startActivity(intent)
    }

}