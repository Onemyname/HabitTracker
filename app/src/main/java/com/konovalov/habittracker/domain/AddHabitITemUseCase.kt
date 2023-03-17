package com.konovalov.habittracker.domain

class AddHabitITemUseCase(private val habitListRepository: HabitListRepository) {
    fun addHabitItem(habitItem: HabitItem){
        habitListRepository.addHabitItem(habitItem)
    }

}