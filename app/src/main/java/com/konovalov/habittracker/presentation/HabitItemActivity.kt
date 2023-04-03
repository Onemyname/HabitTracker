package com.konovalov.habittracker.presentation

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.konovalov.habittracker.R

class HabitItemActivity : AppCompatActivity() {
    private lateinit var viewModel: HabitItemViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habit_item)

    }
}