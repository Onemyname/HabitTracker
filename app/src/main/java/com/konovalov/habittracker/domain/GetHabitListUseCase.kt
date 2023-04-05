package com.konovalov.habittracker.domain

import androidx.lifecycle.LiveData

class GetHabitListUseCase(private val habitListRepository: HabitListRepository) {
    fun getHabitList(): LiveData<List<HabitItem>> = habitListRepository.getHabitList()

}
