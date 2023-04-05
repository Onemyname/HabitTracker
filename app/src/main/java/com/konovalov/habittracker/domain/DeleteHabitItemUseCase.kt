package com.konovalov.habittracker.domain

class DeleteHabitItemUseCase(private val habitListRepository: HabitListRepository) {
    fun deleteHabitItem(habitItem: HabitItem) {
        habitListRepository.deleteHabitItem(habitItem)
    }

}