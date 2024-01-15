package com.example.homebalance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.RatingBar
import com.example.homebalance.Classes.GlobalVariables
import com.example.homebalance.Classes.Review
import com.example.homebalance.Classes.Task
import com.example.homebalance.Classes.User
import com.example.homebalance.Interfaces.ReviewI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ReviewActivity : AppCompatActivity() {

    private lateinit var ratingBar : RatingBar
    private lateinit var et_comment : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)
    }

    fun openHome(v: View){
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }
    fun openProfile(v: View){
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }

    fun doReview(v:View){

        ratingBar = findViewById(R.id.ratingBar)
        et_comment = findViewById(R.id.editTextDescription)

        val retrofit = Retrofit.Builder()
            .baseUrl(GlobalVariables.HOMEBALANCE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(ReviewI :: class.java)

        val TaskId = intent.extras?.getInt("task_id")

        val reviewData = Review(
            null,
            ratingBar.rating,
            et_comment.text.toString(),
            null
        )

        val call = TaskId?.let { service.addReview(it, reviewData) }

        if (call != null) {
            call.enqueue(object : Callback<Any>{
                override fun onResponse(call: Call<Any>, response: Response<Any>) {
                    finish()
                }

                override fun onFailure(call: Call<Any>, t: Throwable) {
                    finish()
                }

            })
        }
    }


}