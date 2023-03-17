package com.konovalov.habittracker.domain

data class HabitItem (
    val id: Int,
    val name: String,
    val count: Number,
    val unit: String,
    val isEnabled: Boolean
        ){
}