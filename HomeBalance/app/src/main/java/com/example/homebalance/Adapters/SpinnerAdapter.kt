package com.example.homebalance.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.homebalance.Classes.User

class SpinnerAdapter(private val context: Context) : BaseAdapter() {
    private var residents: List<User> = listOf()
    fun setResidents(residentsList: List<User>) {
        residents = residentsList
        notifyDataSetChanged()
    }
    override fun getCount(): Int {
        return residents.size
    }

    override fun getItem(position: Int): Any {
        return residents[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder

        if (convertView == null) {
            // Inflar o layout do item do Spinner se a view de conversão (convertView) for nula
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(android.R.layout.simple_spinner_item, parent, false)

            // Inicializar o viewHolder
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            // Usar a view reciclada se convertView não for nula
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        // Configurar o texto do item do Spinner com o nome do residente
        viewHolder.textView.text = residents[position].name ?: ""

        return view
    }

    // ViewHolder para manter as referências dos elementos de UI do item do Spinner
    private class ViewHolder(view: View) {
        val textView: TextView = view.findViewById(android.R.id.text1)
    }
}
