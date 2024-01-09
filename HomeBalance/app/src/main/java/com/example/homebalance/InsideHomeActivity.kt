package com.example.homebalance

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.GridView
import android.widget.ListView
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
    private lateinit var taskListView : ListView
    private val ADD_RESIDENT_REQUEST = 2
    private var userId: Int? = null
    lateinit var taskList : List<Task>

    private val retrofit = Retrofit.Builder()
        .baseUrl(HOMEBALANCE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val residentService = retrofit.create(ResidentI::class.java)
    private val tarefaService = retrofit.create(TaskI::class.java)



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inside_home)

        recyclerView = findViewById(R.id.horizontalscroll)
        adapter = HorizontalListViewAdapter()

        val houseId = intent.extras?.getInt("home_id")
        if (houseId != null) {
            getResidentsByHouseId(houseId)
            val call = houseId.let { tarefaService.listTarefaById(it) }

            call?.enqueue(object : Callback<List<Task>> {
                override fun onResponse(call: Call<List<Task>>, response: Response<List<Task>>) {
                    if (response.isSuccessful) {
                        val tasks = response.body()
                        tasks?.let {
                            taskList = it
                            val taskAdapter = TaskAdapter(this@InsideHomeActivity, taskList)
                            taskListView = findViewById(R.id.listViewTasks)
                            taskListView.adapter = taskAdapter
                        }
                    } else {
                        Log.e("InsideHomeActivity", "Unsuccessful response: ${response.code()}")
                        // Log error code or message if needed
                    }
                }

                override fun onFailure(call: Call<List<Task>>, t: Throwable) {
                    Log.e("InsideHomeActivity", "Call failed: ${t.message}")
                    // Log failure reason if needed
                }
            })
        }

        recyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.adapter = adapter

        userId = intent.extras?.getInt("user_id")
    }

    fun openHome(v: View) {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("user_id", userId)
        startActivity(intent)
    }

    fun openProfile(v: View) {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
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
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
            }
        })
    }



    fun CreateTask(v: View) {
        val intent = Intent(this@InsideHomeActivity, AddTaskActivity::class.java)
        startActivity(intent)
    }

    fun goInviteResident(v: View) {
        val houseId = intent.extras?.getInt("home_id")
        val intent = Intent(this@InsideHomeActivity, AddResidentActivity::class.java)
        intent.putExtra("house_id", houseId)
        Log.d("HouseId", "$houseId")
        startActivityForResult(intent, ADD_RESIDENT_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_RESIDENT_REQUEST) {
            if (resultCode == RESULT_OK) {
                val houseId = intent.extras?.getInt("home_id")
                houseId?.let { getResidentsByHouseId(it) }
            }
        }
    }

}