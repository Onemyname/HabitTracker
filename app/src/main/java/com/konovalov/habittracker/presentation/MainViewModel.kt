package com.konovalov.habittracker.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.konovalov.habittracker.data.HabitListRepositoryImpl
import com.konovalov.habittracker.domain.DeleteHabitItemUseCase
import com.konovalov.habittracker.domain.EditHabitItemUseCase
import com.konovalov.habittracker.domain.GetHabitListUseCase
import com.konovalov.habittracker.domain.HabitItem

class MainViewModel: ViewModel() {

    private val repository = HabitListRepositoryImpl

    private val getHabitListUseCase = GetHabitListUseCase(repository)
    private val deleteHabitItemUseCase = DeleteHabitItemUseCase(repository)
    private val editHabitItemUseCase = EditHabitItemUseCase(repository)

    val habitList = getHabitListUseCase.getHabitList()


    fun deleteHabitItem(habitItem: HabitItem){
       deleteHabitItemUseCase.deleteHabitItem(habitItem)
    }

    fun changeEnableState(habitItem: HabitItem){
        val newItem = habitItem.copy(isEnabled = !habitItem.isEnabled)
        editHabitItemUseCase.editHabitItem(newItem)
    }
}