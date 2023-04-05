package com.konovalov.habittracker.domain

class EditHabitItemUseCase(private val habitListRepository: HabitListRepository) {
    fun editHabitItem(habitItem: HabitItem) {
        habitListRepository.editHabitItem(habitItem)
    }

}