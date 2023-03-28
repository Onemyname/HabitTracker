package com.konovalov.habittracker.presentation

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.konovalov.habittracker.R
import com.konovalov.habittracker.domain.HabitItem

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var habitListAdapter: HabitListAdapter
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.habitList.observe(this){
            habitListAdapter.habitList = it
        }
    }

    private fun setupRecyclerView(){
        val rvHabitList = findViewById<RecyclerView>(R.id.rv_habit_list)
        val enabledView = HabitListAdapter.ENABLED_VIEW_TYPE
        val disabledView = HabitListAdapter.DISABLED_VIEW_TYPE
        val poolSize = HabitListAdapter.MAX_POOL_SIZE
        habitListAdapter = HabitListAdapter()

        with(rvHabitList){
            adapter = habitListAdapter
            recycledViewPool.setMaxRecycledViews(enabledView,poolSize)
            recycledViewPool.setMaxRecycledViews(disabledView,poolSize)
        }

        habitListAdapter.onHabitItemLongClickListener = {
                viewModel.changeEnableState(it)
            }

        habitListAdapter.onHabitItemClickListener = {
                Log.d("MainActivity", it.toString())
        }

        }
    }