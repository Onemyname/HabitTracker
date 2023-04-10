package com.konovalov.habittracker.presentation

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.core.view.get
import androidx.fragment.app.FragmentContainer
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper as I_T_H
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout
import com.konovalov.habittracker.R

class MainActivity : AppCompatActivity(), HabitItemFragment.OnEditingFinishedListener {

    private lateinit var viewModel: MainViewModel
    private lateinit var habitListAdapter: HabitListAdapter
    private var habitItemContainer: FragmentContainerView? = null

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        habitItemContainer = findViewById(R.id.habit_item_container)
        setupRecyclerView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.habitList.observe(this) {
            habitListAdapter.submitList(it)
        }
        val addButton: FloatingActionButton = findViewById(R.id.button_add_habit_item)
        addButton.setOnClickListener {
            if(isOnePaneMode()) {
                val intent = HabitItemActivity.newIntentAddItem(this)
                startActivity(intent)
            }
            else{
                launchFragment(HabitItemFragment.newInstanceAddItem())
                }

            }
        }

    override fun onEditingFinished() {
        Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_SHORT).show()
        supportFragmentManager.popBackStack()
    }

    private fun isOnePaneMode(): Boolean{
        return habitItemContainer == null
    }

    private fun launchFragment(fragment: HabitItemFragment){
        with(supportFragmentManager){
            popBackStack()
            beginTransaction()
                .replace(habitItemContainer!!.id, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun setupRecyclerView() {
        val rvHabitList = findViewById<RecyclerView>(R.id.rv_habit_list)
        val enabledView = HabitListAdapter.ENABLED_VIEW_TYPE
        val disabledView = HabitListAdapter.DISABLED_VIEW_TYPE
        val poolSize = HabitListAdapter.MAX_POOL_SIZE
        habitListAdapter = HabitListAdapter()

        with(rvHabitList) {
            adapter = habitListAdapter
            recycledViewPool.setMaxRecycledViews(enabledView, poolSize)
            recycledViewPool.setMaxRecycledViews(disabledView, poolSize)
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
            if(isOnePaneMode()) {
                val intent = HabitItemActivity.newIntentEditItem(this, it.id)
                startActivity(intent)
            }
            else{
                launchFragment(HabitItemFragment.newInstanceEditItem(it.id))
            }
        }
    }

    private fun setupOnLongClick() {
        habitListAdapter.onHabitItemLongClickListener = {
            viewModel.changeEnableState(it)
        }
    }
}