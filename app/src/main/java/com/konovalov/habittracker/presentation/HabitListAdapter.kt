package com.konovalov.habittracker.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.konovalov.habittracker.R
import com.konovalov.habittracker.domain.HabitItem

class HabitListAdapter: ListAdapter<HabitItem, HabitItemViewHolder>(HabitItemDiffCallBack()) {

    companion object{
        const val ENABLED_VIEW_TYPE = 1
        const val DISABLED_VIEW_TYPE = -1
        const val MAX_POOL_SIZE = 15
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
        val enabled = getItem(position).isEnabled

        return if(enabled)  ENABLED_VIEW_TYPE else DISABLED_VIEW_TYPE
    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HabitItemViewHolder, position: Int) {
        val habitItem = getItem(position)

        with(holder){
            tvName.text = habitItem.name
            tvCount.text = habitItem.count.toString()
            tvUnit.text = " ${habitItem.unit}"
        }

        holder.view.setOnLongClickListener{
            onHabitItemLongClickListener?.invoke(habitItem)
            true
        }
        holder.view.setOnClickListener{
            onHabitItemClickListener?.invoke(habitItem)
        }
    }
}


