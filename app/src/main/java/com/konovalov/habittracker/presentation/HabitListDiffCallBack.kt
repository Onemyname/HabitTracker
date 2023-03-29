package com.konovalov.habittracker.presentation

import androidx.recyclerview.widget.DiffUtil
import com.konovalov.habittracker.domain.HabitItem

class HabitListDiffCallBack  (
    private val oldList : List<HabitItem>,
    private val newList : List<HabitItem>)
    : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
    oldList[oldItemPosition].id == newList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
         oldList[oldItemPosition] == newList[newItemPosition]


}