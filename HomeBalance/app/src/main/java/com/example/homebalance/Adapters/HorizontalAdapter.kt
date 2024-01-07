package com.example.homebalance.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.homebalance.Classes.User
import com.example.homebalance.R

class HorizontalListViewAdapter : RecyclerView.Adapter<HorizontalListViewAdapter.ResidentViewHolder>() {
    private var residents: List<User> = listOf()

    fun setResidents(residentsList: List<User>) {
        residents = residentsList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResidentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.horizontal_listview_adapter, parent, false)
        return ResidentViewHolder(view)
    }

    override fun onBindViewHolder(holder: ResidentViewHolder, position: Int) {
        val resident = residents[position]
        holder.bind(resident)
    }

    override fun getItemCount(): Int {
        return residents.size
    }

    class ResidentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(resident: User) {
            itemView.findViewById<TextView>(R.id.tv_adapter).text = resident.name
        }
    }
}
