package com.konovalov.habittracker.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.konovalov.habittracker.R
import com.konovalov.habittracker.domain.HabitItem

class HabitListAdapter: RecyclerView.Adapter<HabitListAdapter.HabitItemViewHolder>() {

    var habitList = listOf<HabitItem>()
    set(value){
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitItemViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_habit_disabled,parent,false)

        return HabitItemViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }


    override fun onBindViewHolder(holder: HabitItemViewHolder, position: Int) {
        val habitItem = habitList[position]
        val status = if(habitItem.isEnabled) "Active" else "Not active"


        holder.view.setOnLongClickListener{
            true
        }
        if(!habitItem.isEnabled){
            holder.tvName.text = "${habitItem.name} $status"
            holder.tvCount.text = habitItem.count.toString()
            holder.tvUnit.text = habitItem.unit
            holder.tvName.setTextColor(ContextCompat.getColor(
                    holder.view.context,
                    android.R.color.holo_red_light))
        }
    }

    override fun getItemCount(): Int = habitList.size

    override fun onViewRecycled(holder: HabitItemViewHolder) {
        super.onViewRecycled(holder)
        holder.tvName.text = ""
        holder.tvCount.text = ""
        holder.tvUnit.text = ""
        holder.tvName.setTextColor(ContextCompat.getColor(
            holder.view.context,
            android.R.color.white))
    }


    class HabitItemViewHolder(val view: View): RecyclerView.ViewHolder(view){
        val tvName = view.findViewById<TextView>(R.id.tv_name)
        val tvCount = view.findViewById<TextView>(R.id.tv_count)
        val tvUnit = view.findViewById<TextView>(R.id.tv_unit)
    }
}


