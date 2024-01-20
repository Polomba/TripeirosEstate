package com.example.homebalance.Adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.homebalance.Classes.Home
import com.example.homebalance.InsideHomeActivity
import com.example.homebalance.R

class HouseDisplayAdapter(context: Context, homes: List<Home>) :
    ArrayAdapter<Home>(context, 0, homes) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItemView = convertView
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(
                R.layout.housedisplay_adapter, parent, false
            )
        }

        val currentHome = getItem(position)


        val houseNameTextView = listItemView!!.findViewById<TextView>(R.id.tv_housename)
        val addressTextView = listItemView.findViewById<TextView>(R.id.address)

        currentHome?.let {
            houseNameTextView.text = it.name
            addressTextView.text = it.address
        }


        return listItemView
    }


}


