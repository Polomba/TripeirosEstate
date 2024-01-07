package com.example.homebalance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.example.homebalance.Classes.GlobalVariables
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

class ProfileActivity : AppCompatActivity() {

    private val retrofit = Retrofit.Builder()
        .baseUrl(GlobalVariables.HOMEBALANCE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val userEmail: EditText = findViewById(R.id.tv_profileEmail)
    val numberOfHouses: EditText = findViewById(R.id.et_profilenrresidencias)
    val numberConcludedTasks: EditText = findViewById(R.id.et_nrofconcludedtasks)

    private val profileService = retrofit.create(UserI::class.java)
    private val residentService = retrofit.create(ResidentI::class.java)
    private val taskservice = retrofit.create(TaskI::class.java)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
    }

    fun openHome(v: View) {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }

    fun openProfile(v: View) {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }

    private fun getNumberOfHouses(houseId: Int) {
        val call = residentService.getResidentsByHouseId(houseId)
        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val residents = response.body()
                    residents?.let {
                        numberOfHouses.setText(numberOfHouses.toString())
                    }
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
            }
        })
    }

    private fun getNumberOfConcludedTasks(userId: Int) {
        val call = taskservice.listTarefaById(userId)
        call.enqueue(object : Callback<List<Task>> {
            override fun onResponse(call: Call<List<Task>>, response: Response<List<Task>>) {
                if (response.isSuccessful) {
                    val tasks = response.body()
                    tasks?.let {
                        val numberOfConcludedTasks =
                            it.count { task -> task.state.equals("Concluida") }
                        numberConcludedTasks.setText(numberOfConcludedTasks.toString())
                    }
                }
            }

            override fun onFailure(call: Call<List<Task>>, t: Throwable) {
            }
        })
    }

    private fun setEmailForUser(userId: Int, editText: EditText) {
        val call = profileService.getUserById(userId)
        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val users = response.body()
                    users?.let {
                        if (users.isNotEmpty()) {
                            val userEmail = users[0].email
                            editText.setText(userEmail)
                        }
                    }
                }
            }
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
            }
        })
    }
}
