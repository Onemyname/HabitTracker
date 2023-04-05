package com.konovalov.habittracker.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.konovalov.habittracker.data.HabitListRepositoryImpl
import com.konovalov.habittracker.domain.AddHabitITemUseCase
import com.konovalov.habittracker.domain.EditHabitItemUseCase
import com.konovalov.habittracker.domain.GetHabitItemUseCase
import com.konovalov.habittracker.domain.HabitItem


class HabitItemViewModel : ViewModel() {

    private val repository = HabitListRepositoryImpl

    private val editHabitItemUseCase = EditHabitItemUseCase(repository)
    private val addHabitItemUseCase = AddHabitITemUseCase(repository)
    private val getHabitItemUseCase = GetHabitItemUseCase(repository)

    private val _habitItem = MutableLiveData<HabitItem>()
    val habitItem: LiveData<HabitItem>
        get() = _habitItem

    private val _errorInputName = MutableLiveData<Boolean>()
    val errorInputName: LiveData<Boolean>
        get() = _errorInputName

    private val _errorInputCount = MutableLiveData<Boolean>()
    val errorInputCount: LiveData<Boolean>
        get() = _errorInputCount

    private val _shouldCloseScreen = MutableLiveData<Unit>()
    val shouldCloseScreen: LiveData<Unit>
        get() = _shouldCloseScreen

    fun addHabitItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val isValidParameters = validateInput(name, count)
        if (isValidParameters) {
            val newHabitItem = HabitItem(name, count, true)
            addHabitItemUseCase.addHabitItem(newHabitItem)
            finishWork()
        }

    }

    fun editHabitItem(inputName: String?, inputCount: String?) {
        val name = parseName(inputName)
        val count = parseCount(inputCount)
        val isValidParameters = validateInput(name, count)
        if (isValidParameters) {
            _habitItem.value?.let {
                val habitItem = it.copy(name = name, count = count)
                editHabitItemUseCase.editHabitItem(habitItem)
                finishWork()
            }
        }
    }


    fun getHabitItem(id: Int) {

        val item = getHabitItemUseCase.getHabitItem(id)
        _habitItem.value = item

    }

    private fun parseName(inputName: String?): String = inputName?.trim() ?: ""

    private fun parseCount(inputCount: String?): Int {
        return try {
            inputCount?.trim()?.toInt() ?: 0
        } catch (e: java.lang.Exception) {
            0
        }
    }

    private fun validateInput(name: String, count: Int): Boolean {
        var result = true
        if (name.isBlank()) {
            _errorInputName.value = true
            result = false
        }
        if (count <= 0) {
            _errorInputCount.value = true
            result = false
        }
        return result
    }

    fun resetErrorInputName() {
        _errorInputName.value = false
    }

    fun resetErrorInputCount() {
        _errorInputCount.value = false
    }

    private fun finishWork() {
        _shouldCloseScreen.value = Unit
    }
}