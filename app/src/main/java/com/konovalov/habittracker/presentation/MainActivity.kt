package com.konovalov.habittracker.presentation

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.konovalov.habittracker.R
import com.konovalov.habittracker.domain.HabitItem

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var llHabitList: LinearLayout

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        llHabitList = findViewById(R.id.ll_habit_list)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.habitList.observe(this){
            showList(it)

        }
    }
    private fun showList(list: List<HabitItem>) {
        llHabitList.removeAllViews()
        for (habit in list) {
            val layoutId = if (habit .isEnabled) {
                R.layout.item_habit_enabled
            } else {
                R.layout.item_habit_disabled
            }
            val view = LayoutInflater.from(this).inflate(layoutId, llHabitList, false)
            val tvName = view.findViewById<TextView>(R.id.tv_name)
            val tvCount = view.findViewById<TextView>(R.id.tv_count)
            val tvUnit = view.findViewById<TextView>(R.id.tv_unit)
            tvName.text = habit.name
            tvCount.text = habit.count.toString()
            tvUnit.text = habit.unit

            view.setOnLongClickListener(){
                viewModel.changeEnableState(habit)
                true
            }
            llHabitList.addView(view)
        }
    }

}