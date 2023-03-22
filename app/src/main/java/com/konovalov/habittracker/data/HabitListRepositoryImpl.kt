package com.konovalov.habittracker.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.konovalov.habittracker.domain.HabitItem
import com.konovalov.habittracker.domain.HabitListRepository

object HabitListRepositoryImpl : HabitListRepository{
    private val habitListLD = MutableLiveData<List<HabitItem>>()
    private val habitList = sortedSetOf<HabitItem>({o1,o2->o1.id.compareTo(o2.id)})
    private var autoIncrementId = 0

    init{
        for (i in 0 until 3){
            val item = HabitItem("Name $i", i, "unit",true)
            addHabitItem(item)
        }
    }

    override fun getHabitList(): LiveData<List<HabitItem>> {

        return habitListLD
    }

    override fun getHabitItem(habitItemId: Int): HabitItem {

       return habitList.find {
           it.id == habitItemId
       } ?: throw java.lang.RuntimeException("Element with $habitItemId not found")
    }

    override fun addHabitItem(habitItem: HabitItem) {
        if(habitItem.id == HabitItem.getUndefinedId()) {
        habitItem.id = autoIncrementId++
        }

        habitList.add(habitItem)
        updateList()
    }

    override fun editHabitItem(habitItem: HabitItem) {
        val oldElement = getHabitItem(habitItem.id)
        habitList.remove(oldElement)
        addHabitItem(habitItem)
    }

    override fun deleteHabitItem(habitItem: HabitItem) {
        habitList.remove(habitItem)
        updateList()
    }

    private fun updateList(){
        habitListLD.value = habitList.toList()
    }
}