package com.example.homebalance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import com.example.homebalance.Adapters.HorizontalListViewAdapter
import com.example.homebalance.Adapters.SpinnerAdapter
import com.example.homebalance.Classes.GlobalVariables
import com.example.homebalance.Classes.HomeResponse
import com.example.homebalance.Classes.Task
import com.example.homebalance.Classes.TaskIdResponse
import com.example.homebalance.Classes.User
import com.example.homebalance.Interfaces.ResidentI
import com.example.homebalance.Interfaces.TaskI
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class AddTaskActivity : AppCompatActivity() {

    private lateinit var et_TaskName: EditText
    private lateinit var dtpicker_end: DatePicker
    private lateinit var imageView: ImageView
    private lateinit var et_ToDo: EditText
    private lateinit var et_addcomment: EditText
    private lateinit var spinnerResponsible: Spinner
    private lateinit var adapter: SpinnerAdapter

    private val retrofit = Retrofit.Builder()
        .baseUrl(GlobalVariables.HOMEBALANCE_URL)
        .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
        .build()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_task)

        // Inicialize o Spinner e o SpinnerAdapter
        spinnerResponsible = findViewById(R.id.spinner_responsible)
        adapter = SpinnerAdapter(this)
        spinnerResponsible.adapter = adapter

        // Restante do seu código
        val homeId = intent.extras?.getInt("house_id")
        if (homeId != null) {
            getResidentsByHouseId(homeId)
        }
    }
    fun openHome(v: View){
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }
    fun openProfile(v: View){
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }

    fun doCreateTask(v:View) {

        et_TaskName = findViewById(R.id.et_TaskName)
        dtpicker_end = findViewById(R.id.dtpicker_end)
        imageView = findViewById(R.id.imageView)
        et_ToDo = findViewById(R.id.et_ToDo)
        //et_addcomment = findViewById(R.id.et_AddComment)



        val homeId = intent.extras?.getInt("house_id")
        val userId = intent.extras?.getInt("user_id")
        if (homeId != null) {
            getResidentsByHouseId(homeId)

        }

        val title = et_TaskName.text.toString()
        val ToDo = et_ToDo.text.toString()
        val year = dtpicker_end.year
        val month = dtpicker_end.month + 1
        val day = dtpicker_end.dayOfMonth

        val formattedDate = String.format("%04d-%02d-%02d", year, month, day)

        val service = retrofit.create(TaskI::class.java)

        val taskData = Task(
            null,
            title,
            ToDo,
            formattedDate,
            "Ativo",
            "limpeza.jpg",
            homeId,
            userId
        )

        val sp = GlobalVariables.getSharedPreferencesContext(this@AddTaskActivity)
        val token = sp.getString("authToken", null)


        val call = service.addTarefa(token, taskData)
        call.enqueue(object : Callback<List<TaskIdResponse>> {
            override fun onResponse(call: Call<List<TaskIdResponse>>, response: Response<List<TaskIdResponse>>) {
                if (response.isSuccessful) {
                    setResult(RESULT_OK)
                } else {
                    Log.d("YourTag", "Task creation failed with code: ${response.code()}")
                }
                finish()
            }

            override fun onFailure(call: Call<List<TaskIdResponse>>, t: Throwable) {
                Log.e("YourTag", "Task creation failed", t)
                finish()
            }
        })


    }

    private fun getResidentsByHouseId(houseId: Int) {
        val residentService = retrofit.create(ResidentI :: class.java)
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


}