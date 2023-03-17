package com.konovalov.habittracker.domain

class GetHabitListUseCase(private val habitListRepository: HabitListRepository)  {
    fun getHabitList() : List<HabitItem> = habitListRepository.getHabitList()

}
