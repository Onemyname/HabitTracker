package com.konovalov.habittracker.domain

interface HabitListRepository {

    fun getHabitList() : List<HabitItem>

    fun getHabitItem(id: Int) : HabitItem

    fun addHabitItem(habitItem: HabitItem)

    fun editHabitItem(habitItem: HabitItem)

    fun deleteHabitItem(habitItem: HabitItem)

}