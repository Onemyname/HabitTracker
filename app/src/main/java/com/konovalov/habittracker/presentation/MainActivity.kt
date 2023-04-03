package com.konovalov.habittracker.presentation

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper as I_T_H
import androidx.recyclerview.widget.RecyclerView
import com.konovalov.habittracker.R


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var habitListAdapter: HabitListAdapter
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        setContentView(R.layout.activity_habit_item)

        setupRecyclerView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.habitList.observe(this){
            habitListAdapter.submitList(it)
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

        setupOnLongClick()

        setupOnFastClick()

        setupSwipe(rvHabitList)


    }

    private fun setupSwipe(rvHabitList: RecyclerView) {
        val callBack = object : I_T_H.SimpleCallback(0, I_T_H.LEFT.or(I_T_H.RIGHT)) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = habitListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteHabitItem(item)
            }
        }

        val itemTouchHelper = I_T_H(callBack)
        itemTouchHelper.attachToRecyclerView(rvHabitList)
    }

    private fun setupOnFastClick() {
        habitListAdapter.onHabitItemClickListener = {
            TODO()
        }
    }

    private fun setupOnLongClick() {
        habitListAdapter.onHabitItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }
}