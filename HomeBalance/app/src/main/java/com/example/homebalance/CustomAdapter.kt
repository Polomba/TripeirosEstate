package com.example.homebalance

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(context: Context) :
    ArrayAdapter<Any>(context, 0) {

    @SuppressLint("ServiceCast")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var rowView = convertView
        val viewHolder: ViewHolder

        if (rowView == null) {
            viewHolder = ViewHolder()
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            rowView = inflater.inflate(R.layout.horizontal_listview_adapter, parent, false)

            viewHolder.imageView1 = rowView.findViewById<ImageView>(R.id.imageView1)
            viewHolder.textView1 = rowView.findViewById<TextView>(R.id.textView1)
            viewHolder.imageView2 = rowView.findViewById<ImageView>(R.id.imageView2)
            viewHolder.textView2 = rowView.findViewById<TextView>(R.id.textView2)
            viewHolder.imageView3 = rowView.findViewById<ImageView>(R.id.imageView3)
            viewHolder.textView3 = rowView.findViewById<TextView>(R.id.textView3)
            viewHolder.imageView4 = rowView.findViewById<ImageView>(R.id.imageView4)
            viewHolder.textView4= rowView.findViewById<TextView>(R.id.textView4)


            // Initialize other views similarly (imageView3, textView3, imageView4, textView4)

            rowView.tag = viewHolder
        } else {
            viewHolder = rowView.tag as ViewHolder
        }

        // Set placeholder images and text
        viewHolder.imageView1.setImageResource(R.drawable.icons8_home_96px_7)
        viewHolder.imageView2.setImageResource(R.drawable.icons8_home_96px_7)
        viewHolder.imageView3.setImageResource(R.drawable.icons8_home_96px_7)
        viewHolder.imageView4.setImageResource(R.drawable.icons8_home_96px_7)


        // Set other ImageViews and TextViews with placeholder data
        // viewHolder.imageView3.setImageResource(R.drawable.placeholder_image)
        // viewHolder.textView3.text = "Placeholder Text 3"
        // ...

        return rowView!!
    }

    internal class ViewHolder {
        lateinit var imageView1: ImageView
        lateinit var textView1: TextView
        lateinit var imageView2: ImageView
        lateinit var textView2: TextView
        lateinit var imageView3: ImageView
        lateinit var textView3: TextView
        lateinit var imageView4: ImageView
        lateinit var textView4: TextView

        // Declare other ImageViews and TextViews in a similar manner
        // var imageView3: ImageView
        // var textView3: TextView
        // ...
    }
}