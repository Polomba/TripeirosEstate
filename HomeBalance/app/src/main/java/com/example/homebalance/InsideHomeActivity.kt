package com.example.homebalance

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.GridView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.homebalance.Adapters.HorizontalListViewAdapter
import com.example.homebalance.Adapters.TaskAdapter
import com.example.homebalance.Classes.GlobalVariables.HOMEBALANCE_URL
import com.example.homebalance.Classes.Task
import com.example.homebalance.Classes.User
import com.example.homebalance.Interfaces.ResidentI
import com.example.homebalance.Interfaces.TaskI
import com.example.homebalance.Interfaces.UserI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class InsideHomeActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: HorizontalListViewAdapter

    private val retrofit = Retrofit.Builder()
        .baseUrl(HOMEBALANCE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val residentService = retrofit.create(ResidentI::class.java)
    private val taskservice = retrofit.create(TaskI::class.java)


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
            getTasksByHouseId(houseId)
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
            }
        })
    }

    private fun getTasksByHouseId(homeid: Int) {
        val call = taskservice.listTarefaById(homeid)
        call.enqueue(object : Callback<List<Task>> {
            override fun onResponse(call: Call<List<Task>>, response: Response<List<Task>>) {
                if (response.isSuccessful) {
                    val taskList = response.body()
                    taskList?.let {
                        val gridView: GridView = findViewById(R.id.gridView)
                        val taskAdapter = TaskAdapter(this@InsideHomeActivity, it,)
                        gridView.adapter = taskAdapter
                    }
                }
            }

            override fun onFailure(call: Call<List<Task>>, t: Throwable) {
            }
        })
    }




    fun CreateTask(V: View) {
        val intent = Intent(this@InsideHomeActivity, AddTaskActivity::class.java)
        startActivity(intent)
    }

    fun goInviteResident(v: View){
        val intent = Intent(this@InsideHomeActivity, AddResidentActivity::class.java)
        startActivity(intent)
    }

}