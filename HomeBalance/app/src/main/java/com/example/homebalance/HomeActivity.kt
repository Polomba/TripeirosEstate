package com.example.homebalance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ListView
import com.example.homebalance.Adapters.HouseDisplayAdapter
import com.example.homebalance.Classes.GlobalVariables
import com.example.homebalance.Classes.Home
import com.example.homebalance.Interfaces.ResidentI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeActivity : AppCompatActivity() {

    private lateinit var listViewHouses: ListView
    private val ADD_HOME_REQUEST = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)



        listViewHouses = findViewById(R.id.lst_houses)
        setupHouseList()


    }

    private fun setupHouseList() {
        val userId = intent.extras?.getInt("user_id")
        val retrofit = Retrofit.Builder()
            .baseUrl(GlobalVariables.HOMEBALANCE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ResidentI::class.java)

        val call = userId?.let { service.getHouseByUserId(it) }

        call?.enqueue(object : Callback<List<Home>> {
            override fun onResponse(call: Call<List<Home>>, response: Response<List<Home>>) {
                if (response.isSuccessful) {
                    val homes: List<Home>? = response.body()
                    homes?.let {
                        val houseAdapter = HouseDisplayAdapter(this@HomeActivity, it)
                        listViewHouses.adapter = houseAdapter
                        houseAdapter.notifyDataSetChanged()
                        listViewHouses.setOnItemClickListener { _, _, position, _ ->
                            val selectedHome: Home = homes[position]
                            val intent = Intent(this@HomeActivity, InsideHomeActivity::class.java)
                            val homeid = selectedHome.id
                            intent.putExtra("home_id", homeid)
                            intent.putExtra("user_id", userId)
                            startActivity(intent)
                        }
                    }
                } else {
                    Log.e("HomeActivity", "Falha na requisição")
                }
            }

            override fun onFailure(call: Call<List<Home>>, t: Throwable) {
                Log.e("HomeActivity", "Erro: ${t.message}")
            }
        })
    }

    fun createHome(v: View) {
        val intent = Intent(this, AddHomeActivity::class.java)
        val userId = intent.extras?.getInt("user_id")
        intent.putExtra("user_id", userId)
        startActivityForResult(intent, ADD_HOME_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_HOME_REQUEST) {
            if (resultCode == RESULT_OK) {
                setupHouseList()
            }
        }
    }


}
