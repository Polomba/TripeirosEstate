package com.example.homebalance

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.TextView
import com.example.homebalance.Classes.Comments
import com.example.homebalance.Classes.GlobalVariables
import com.example.homebalance.Classes.Task
import com.example.homebalance.Classes.User
import com.example.homebalance.Interfaces.CommentI
import com.example.homebalance.Interfaces.TaskI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class TaskDetailActivity : AppCompatActivity() {

    private lateinit var tv_TaskName: TextView
    private lateinit var dtpicker_end: DatePicker // Supondo que você tenha um DatePicker no layout
    private lateinit var imageView: ImageView
    private lateinit var tv_ToDo: TextView
    private lateinit var tv_state: TextView
    private lateinit var tv_comment: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_detail)

        tv_TaskName = findViewById(R.id.tv_TaskName)
        dtpicker_end = findViewById(R.id.dtpicker_end)
        imageView = findViewById(R.id.imageView)
        tv_ToDo = findViewById(R.id.tv_ToDo)
        tv_state = findViewById(R.id.tv_state)
        tv_comment = findViewById(R.id.tv_comment)

        val retrofit = Retrofit.Builder()
            .baseUrl(GlobalVariables.HOMEBALANCE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(TaskI::class.java)

        val taskId = intent.extras?.getInt("task_id")
        Log.d("TaskDetail","$taskId")

        val call = taskId?.let { service.listTarefaByTaskId(it) }

        call?.enqueue(object : Callback<List<Task>>{
            override fun onResponse(call: Call<List<Task>>, response: Response<List<Task>>) {
                if (response.isSuccessful) {
                    val tasks = response.body()
                    tasks?.let {
                        if (it.isNotEmpty()) {
                            val task = it[0]

                            tv_TaskName.text = task.title
                            Log.d("Titulo","${task.title}")
                            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                            val date = dateFormat.parse(task.date.toString())
                            val calendar = Calendar.getInstance()
                            calendar.time = date

                            // Definir a data no DatePicker
                            dtpicker_end.init(
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MONTH),
                                calendar.get(Calendar.DAY_OF_MONTH),
                                null
                            )
                            // Carregue a imagem usando a biblioteca de carregamento de imagens (Glide, Picasso, etc.)
                            // Exemplo usando Glide:
                            // Glide.with(this).load(task.photo).into(imageView)
                            tv_ToDo.text = task.description
                            tv_state.text = task.state
                            getComments(taskId)


                        }
                    }
                }
            }
            override fun onFailure(call: Call<List<Task>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    fun doReview(V: View){
        val taskId = intent.extras?.getInt("task_id")
        val intent = Intent(this@TaskDetailActivity, ReviewActivity::class.java)
        intent.putExtra("task_id", taskId)
        startActivity(intent)
    }

    fun openHome(v:View){
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }
    fun openProfile(v:View){
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
    }

    fun getComments(taskId : Int){
        val retrofit = Retrofit.Builder()
            .baseUrl(GlobalVariables.HOMEBALANCE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(CommentI::class.java)

        val call = service.listCommentsByTaskId(taskId)

        call.enqueue(object : Callback<List<Comments>> {
            override fun onResponse(call: Call<List<Comments>>, response: Response<List<Comments>>) {
                if (response.isSuccessful) {
                    val tasks = response.body()
                    if (tasks != null && tasks.isNotEmpty()) {
                        tv_comment.text = tasks[0].comment
                    } else {
                    }
                } else {
                }
            }
            override fun onFailure(call: Call<List<Comments>>, t: Throwable) {

            }
        })
    }
}
