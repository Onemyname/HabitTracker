package com.konovalov.habittracker.presentation

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.konovalov.habittracker.R

class HabitItemViewHolder(val view: View): RecyclerView.ViewHolder(view){
    val tvName = view.findViewById<TextView>(R.id.tv_name)
    val tvCount = view.findViewById<TextView>(R.id.tv_count)
    val tvUnit = view.findViewById<TextView>(R.id.tv_unit)
}