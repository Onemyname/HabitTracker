package com.konovalov.habittracker.domain

import androidx.recyclerview.widget.ItemTouchHelper


data class HabitItem (

    val name: String,
    val count: Number,
    val unit: String,
    var isEnabled: Boolean,
    var id: Int = UNDEFINED_ID
){
    companion object{
        private const val UNDEFINED_ID = -1

        fun getUndefinedId(): Int {
            return UNDEFINED_ID
        }
    }


}