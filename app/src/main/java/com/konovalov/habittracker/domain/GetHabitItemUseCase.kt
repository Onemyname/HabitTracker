package com.konovalov.habittracker.domain

class GetHabitItemUseCase(private val habitListRepository: HabitListRepository) {
    fun getHabitItem(id: Int): HabitItem = habitListRepository.getHabitItem(id)

}