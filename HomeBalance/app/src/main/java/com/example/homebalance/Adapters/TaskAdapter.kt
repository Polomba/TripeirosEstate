package com.example.homebalance.Adapters

import android.content.Context
import android.graphics.Color
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.homebalance.Classes.GlobalVariables
import com.example.homebalance.Classes.GlobalVariables.HOMEBALANCE_URL
import com.example.homebalance.Classes.Task
import com.example.homebalance.Classes.User
import com.example.homebalance.Interfaces.TaskI
import com.example.homebalance.Interfaces.UserI
import com.example.homebalance.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File

class TaskAdapter(private val context: Context, private val taskList: List<Task>) : BaseAdapter() {
    override fun getCount(): Int {
        return taskList.size
    }

    override fun getItem(position: Int): Any {
        return taskList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val gridViewItem = convertView ?: inflater.inflate(R.layout.task_adapter, parent, false)

        val task = taskList[position]

        val taskTitleTextView = gridViewItem.findViewById<TextView>(R.id.TaskTitle)
        val responsibleTextView = gridViewItem.findViewById<TextView>(R.id.tv_responsible)
        val taskImage = gridViewItem.findViewById<ImageView>(R.id.iv_taskpic)


        val imagePath = task.photo
        val directoryPath = "${Environment.getExternalStorageDirectory()}"
        val fullPath = "$directoryPath$imagePath"
        

        val file = File(fullPath)
        Log.d("Photo", "File absolute path: ${file.absolutePath}")
        Log.d("Photo", "File exists: ${file.exists()}")

        if (file.exists()) {
            Glide.with(context).load(file).into(taskImage)
        } else {
            taskImage.setImageResource(R.drawable.janitor_60px)
        }


        taskTitleTextView.text = task.title ?: ""
        val dateString = task.date?.toString() ?: ""
        val dateWithoutTime = dateString.split(" ")[0]

        //endDateTextView.text = dateWithoutTime

        val retrofit = Retrofit.Builder()
            .baseUrl(HOMEBALANCE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(UserI::class.java)

        val userId = task.userid
        userId?.let { id ->
            val call = service.getUserById(id)

            call.enqueue(object : Callback<List<User>> {
                override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                    if (response.isSuccessful) {
                        val users = response.body()
                        if (!users.isNullOrEmpty()) {
                            val userName = users[0].name ?: ""
                            responsibleTextView.text = "$userName"
                        } else {
                        }
                    } else {
                    }
                }
                override fun onFailure(call: Call<List<User>>, t: Throwable) {
                }
            })
        }


        return gridViewItem
    }


}


