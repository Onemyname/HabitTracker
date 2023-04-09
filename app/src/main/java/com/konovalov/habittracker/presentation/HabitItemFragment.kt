package com.konovalov.habittracker.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.view.OneShotPreDrawListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputLayout
import com.konovalov.habittracker.R
import com.konovalov.habittracker.domain.HabitItem

class HabitItemFragment : Fragment() {

    private lateinit var viewModel: HabitItemViewModel
    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private lateinit var tilName: TextInputLayout
    private lateinit var tilCount: TextInputLayout
    private lateinit var etName: EditText
    private lateinit var etCount: EditText
    private lateinit var saveButton: Button

    private var screenMode: String = MODE_UNKNOWN
    private var habitItemId: Int = HabitItem.getUndefinedId()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is OnEditingFinishedListener){
            onEditingFinishedListener = context
        }
        else{
            throw RuntimeException("Activity must implement OnEditingFinishedListener")
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParameters()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_habit_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[HabitItemViewModel::class.java]
        initViews(view)
        addTextChangeListeners()
        launchMode()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_name)
            } else {
                null
            }
            tilName.error = message

        }

        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_count)
            } else {
                null
            }
            tilCount.error = message
        }

        viewModel.shouldCloseScreen.observe(viewLifecycleOwner) {
            onEditingFinishedListener.onEditingFinished()
        }
    }

    private fun launchMode() {
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun addTextChangeListeners() {
        etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(p0: Editable?) {}

        })
        etCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                viewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }

    private fun launchEditMode() {
        viewModel.getHabitItem(habitItemId)
        viewModel.habitItem.observe(viewLifecycleOwner) {
            etName.setText(it.name)
            etCount.setText(it.count.toString())
        }

        saveButton.setOnClickListener {
            val newInputName = etName.text?.toString()
            val newInputCount = etCount.text?.toString()
            viewModel.editHabitItem(newInputName, newInputCount)

        }
    }

    private fun launchAddMode() {
        saveButton.setOnClickListener {
            val inputName = etName.text?.toString()
            val inputCount = etCount.text?.toString()
            viewModel.addHabitItem(inputName, inputCount)
        }
    }

    private fun initViews(view: View) {
        tilName = view.findViewById(R.id.til_name)
        tilCount = view.findViewById(R.id.til_count)
        etCount = view.findViewById(R.id.et_count)
        etName = view.findViewById(R.id.et_name)
        saveButton = view.findViewById(R.id.save_button)
    }

    interface OnEditingFinishedListener{
        fun onEditingFinished()
    }
    companion object {
        private const val SCREEN_MODE = "extra_mode"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val HABIT_ITEM_ID = "extra_habit_item_id"
        private const val MODE_UNKNOWN = ""

        fun newInstanceAddItem(): HabitItemFragment {

            return HabitItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditItem(habitItemId: Int): HabitItemFragment {
            return HabitItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putInt(HABIT_ITEM_ID, habitItemId)
                }
            }
        }
    }


    private fun parseParameters() {
        val args = requireArguments()
        if (!args.containsKey(SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }

        val mode = args.getString(SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown screen mode $screenMode")
        }

        screenMode = mode
        if(screenMode == MODE_EDIT){
            if(!args.containsKey(HABIT_ITEM_ID)){
                    throw RuntimeException("Param habit item id is absent")
                }
            habitItemId = args.getInt(HABIT_ITEM_ID, HabitItem.getUndefinedId())
        }

    }
}