package com.konovalov.habittracker.domain

import androidx.lifecycle.LiveData

interface HabitListRepository {

    fun getHabitList(): LiveData<List<HabitItem>>

    fun getHabitItem(id: Int): HabitItem

    fun addHabitItem(habitItem: HabitItem)

    fun editHabitItem(habitItem: HabitItem)

    fun deleteHabitItem(habitItem: HabitItem)

}