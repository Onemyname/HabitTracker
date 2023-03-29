package com.konovalov.habittracker.presentation

import androidx.recyclerview.widget.DiffUtil
import com.konovalov.habittracker.domain.HabitItem

class HabitItemDiffCallBack: DiffUtil.ItemCallback<HabitItem>() {
    override fun areItemsTheSame(oldItem: HabitItem, newItem: HabitItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: HabitItem, newItem: HabitItem): Boolean {
        return oldItem == newItem
    }


}