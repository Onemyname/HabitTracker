package com.konovalov.habittracker.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.konovalov.habittracker.R
import com.konovalov.habittracker.domain.HabitItem

class HabitItemActivity : AppCompatActivity() {

    private var screenMode = MODE_UNKNOWN
    private var habitItemId = HabitItem.getUndefinedId()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_habit_item)
        parseIntent()
        if(savedInstanceState == null) launchMode()

    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val EXTRA_HABIT_ITEM_ID = "extra_habit_item_id"
        private const val MODE_UNKNOWN = ""

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, HabitItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, habitItemId: Int): Intent {
            val intent = Intent(context, HabitItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_HABIT_ITEM_ID, habitItemId)
            return intent
        }
    }


    private fun launchMode() {
        val fragment = when (screenMode) {
            MODE_EDIT -> HabitItemFragment.newInstanceEditItem(habitItemId)
            MODE_ADD -> HabitItemFragment.newInstanceAddItem()
            else-> throw RuntimeException("Unknown screen mode: $screenMode")
        }
    supportFragmentManager.beginTransaction()
        .replace(R.id.habit_item_container, fragment)
        .commit()
    }

    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw java.lang.RuntimeException("Param screen mode is absent")
        }

        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)

        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw java.lang.RuntimeException("Unknown screen mode $mode")
        }

        screenMode = mode

        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra((EXTRA_HABIT_ITEM_ID))) {
                throw java.lang.RuntimeException("Param habit item id is absent")
            }
            habitItemId = intent.getIntExtra(EXTRA_HABIT_ITEM_ID, -1)
        }

    }
}