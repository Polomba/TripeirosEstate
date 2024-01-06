package com.example.homebalance.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.example.homebalance.Classes.Task
import com.example.homebalance.Classes.User
import com.example.homebalance.Interfaces.UserI
import com.example.homebalance.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        var view = convertView
        val holder: ViewHolder

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.task_adapter, parent, false)
            holder = ViewHolder()
            holder.taskImageView = view.findViewById(R.id.iv_taskpic)
            holder.taskTextView = view.findViewById(R.id.textView)
            holder.taskTextView2 = view.findViewById(R.id.tv_responsible)
            view.tag = holder
        } else {
            holder = view.tag as ViewHolder
        }

        val task = taskList[position]

        holder.taskTextView.text = task.title
        holder.taskTextView2.text = task.user.toString()
         holder.taskImageView.setImageResource(R.drawable.icons8_home_96px_7)

        return view!!
    }

    private class ViewHolder {
        lateinit var taskImageView: ImageView
        lateinit var taskTextView: TextView
        lateinit var taskTextView2: TextView
    }

}
