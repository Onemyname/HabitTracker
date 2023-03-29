package com.konovalov.habittracker.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.konovalov.habittracker.R
import com.konovalov.habittracker.domain.HabitItem

class HabitListAdapter: RecyclerView.Adapter<HabitListAdapter.HabitItemViewHolder>() {

    var habitList = listOf<HabitItem>()
    set(value){
        val callBack = HabitListDiffCallBack(habitList,value)
        val diffResult = DiffUtil.calculateDiff(callBack)
        diffResult.dispatchUpdatesTo(this)
        field = value
    }

    var onHabitItemLongClickListener :((HabitItem) -> Unit)? = null
    var onHabitItemClickListener : ((HabitItem)->Unit)? = null



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitItemViewHolder {
        val layout = when(viewType){
            ENABLED_VIEW_TYPE -> R.layout.item_habit_enabled
            DISABLED_VIEW_TYPE-> R.layout.item_habit_disabled
            else -> throw java.lang.RuntimeException("Unknown viewType: $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(layout,parent,false)

        return HabitItemViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        val enabled = habitList[position].isEnabled
        return if(enabled)  ENABLED_VIEW_TYPE else DISABLED_VIEW_TYPE
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HabitItemViewHolder, position: Int) {
        val habitItem = habitList[position]
        with(holder){
            tvName.text = habitItem.name
            tvCount.text = habitItem.count.toString()
            tvUnit.text = habitItem.unit
        }

        holder.view.setOnLongClickListener{
            onHabitItemLongClickListener?.invoke(habitItem)
            true
        }
        holder.view.setOnClickListener{
            onHabitItemClickListener?.invoke(habitItem)
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

    companion object{
        const val ENABLED_VIEW_TYPE = 1
        const val DISABLED_VIEW_TYPE = -1
        const val MAX_POOL_SIZE = 15
    }
}


