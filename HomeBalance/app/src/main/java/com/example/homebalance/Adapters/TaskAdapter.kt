package com.example.homebalance.Adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.homebalance.Classes.GlobalVariables
import com.example.homebalance.Classes.Task
import com.example.homebalance.Classes.User
import com.example.homebalance.Interfaces.UserI
import com.example.homebalance.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TaskAdapter(private val context: Context, private val taskList: List<Task>) : BaseAdapter() {

    override fun getCount(): Int {
        Log.d("TaskAdapter", "getCount called")
        return taskList.size
    }

    override fun getItem(position: Int): Any {
        Log.d("TaskAdapter", "getItem called at position $position")
        return taskList[position]
    }

    override fun getItemId(position: Int): Long {
        Log.d("TaskAdapter", "getItemId called for position $position")
        return position.toLong()
    }

    private val retrofit = Retrofit.Builder()
        .baseUrl(GlobalVariables.HOMEBALANCE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        Log.d("TaskAdapter", "getView called for position $position")
        var view = convertView
        val holder: ViewHolder

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.task_adapter, parent, false)
            holder = ViewHolder()
            holder.taskTextView = view.findViewById(R.id.TaskTitle)
            holder.responsibleTextView = view.findViewById(R.id.tv_responsible)
            holder.endDateTextView = view.findViewById(R.id.tv_endDate)
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }

        val task = taskList[position]

        holder.taskTextView.text = task.title
        holder.endDateTextView.text = task.date.toString()

        task.userid?.let { userId ->
            getUserById(userId) { user ->
                holder.responsibleTextView.text = ("Responsible:" + user?.name) ?: "Unknown"
            }
        }

        return view!!
    }

    private class ViewHolder {
        lateinit var taskTextView: TextView
        lateinit var responsibleTextView: TextView
        lateinit var endDateTextView: TextView
    }

    private fun getUserById(userId: Int, callback: (User?) -> Unit) {
        Log.d("TaskAdapter", "getUserById called for userId $userId")
        val service = retrofit.create(UserI::class.java)
        val call = service.getUserById(userId)

        call.enqueue(object : Callback<List<User>>{
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val users = response.body()
                    if (users != null && users.isNotEmpty()) {
                        callback(users[0])
                    } else {
                        callback(null)
                    }
                } else {
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                callback(null)
            }
        })
    }
}

