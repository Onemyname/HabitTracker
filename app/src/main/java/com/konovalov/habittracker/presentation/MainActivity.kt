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
    private lateinit var adapter: HabitListAdapter
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.habitList.observe(this){
            adapter.habitList = it
        }
    }

    private fun setupRecyclerView(){
        val rvHabitList = findViewById<RecyclerView>(R.id.rv_habit_list)
        adapter = HabitListAdapter()
        rvHabitList.adapter = adapter
    }


}