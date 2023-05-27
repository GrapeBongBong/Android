package com.example.android_bong.view.main.talentexchange.create

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.android_bong.R
import com.example.android_bong.common.ViewBindingActivity
import com.example.android_bong.databinding.ActivityTalentExchangeCreateBinding
import com.example.android_bong.extension.setResultRefresh
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TalentExchangeCreateActivity : ViewBindingActivity<ActivityTalentExchangeCreateBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityTalentExchangeCreateBinding
        get() = ActivityTalentExchangeCreateBinding::inflate

    companion object {
        fun getIntent(context: Context): Intent {
            return Intent(context, TalentExchangeCreateActivity::class.java)
        }
    }

    private val viewModel: TalentExchangeCreateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.toolbar.setTitle(R.string.exchange_talent_posting)
        setSupportActionBar(binding.toolbar)
        val ab = supportActionBar!!
        ab.setDisplayHomeAsUpEnabled(true)
        initEvent()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    updateUi(it)
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateUi(uiState: TalentExchangeCreateUiState) = with(binding) {

        if (uiState.isSuccessPosting) {
            setResultRefresh()
            finish()
        }

        if (uiState.userMessage != null) {
            showSnackBar(uiState.userMessage)
            viewModel.userMessageShown()
        }

        createButton.apply {
            isEnabled = uiState.isInputValid
        }
    }

    private fun initEvent() = with(binding) {

        title.addTextChangedListener {
            if (it != null) {
                viewModel.updateTitle(it.toString())
            }
        }

        content.addTextChangedListener {
            if (it != null) {
                viewModel.updateContent(it.toString())
            }
        }

        giveText.addTextChangedListener {
            if (it != null) {
                viewModel.updateGiveTalent(it.toString())
            }
        }

        takeText.addTextChangedListener {
            if (it != null) {
                viewModel.updateTakeTalent(it.toString())
            }
        }

        mondayButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.updatePossibleDay(getString(R.string.monday))
            } else {
                viewModel.updateImPossibleDay(getString(R.string.monday))
            }
        }

        tuesdayButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.updatePossibleDay(getString(R.string.tuesday))
            } else {
                viewModel.updateImPossibleDay(getString(R.string.tuesday))
            }
        }

        wednesdayButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.updatePossibleDay(getString(R.string.wednesday))
            } else {
                viewModel.updateImPossibleDay(getString(R.string.wednesday))
            }
        }

        thursdayButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.updatePossibleDay(getString(R.string.thursday))
            } else {
                viewModel.updateImPossibleDay(getString(R.string.thursday))
            }
        }

        fridayButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.updatePossibleDay(getString(R.string.friday))
            } else {
                viewModel.updateImPossibleDay(getString(R.string.friday))
            }

        }

        saturdayButton.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                viewModel.updatePossibleDay(getString(R.string.saturday))
            } else {
                viewModel.updateImPossibleDay(getString(R.string.saturday))
            }
        }

        sundayButton.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                viewModel.updatePossibleDay(getString(R.string.sunday))
            } else {
                viewModel.updateImPossibleDay(getString(R.string.saturday))
            }
        }

        timeGroup.setOnCheckedChangeListener { group, checkedId ->
            when (group.id) {
                R.id.timeGroup ->
                    when (checkedId) {
                        R.id.dawn_button -> viewModel.updatePossibleTime(getString(R.string.dawn))
                        R.id.morning_button -> viewModel.updatePossibleTime(getString(R.string.morning))
                        R.id.lunch_button -> viewModel.updatePossibleTime(getString(R.string.launch))
                        R.id.afternoon_button -> viewModel.updatePossibleTime(getString(R.string.afternoon))
                        R.id.dinner_button -> viewModel.updatePossibleTime(getString(R.string.dinner))
                    }
            }
        }

        giveSpinner.adapter = ArrayAdapter.createFromResource(
            this@TalentExchangeCreateActivity,
            R.array.talent_array,
            android.R.layout.simple_spinner_item
        )

        takeSpinner.adapter = ArrayAdapter.createFromResource(
            this@TalentExchangeCreateActivity,
            R.array.talent_array,
            android.R.layout.simple_spinner_item
        )

        giveSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.updateGiveCate(giveSpinner.getItemAtPosition(position).toString())
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }

        takeSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    viewModel.updateTakeCate(takeSpinner.getItemAtPosition(position).toString())
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }

        createButton.setOnClickListener {
            viewModel.createPost()
        }

    }

    private fun showSnackBar(message: String) {
        Snackbar.make(this, binding.root, message, Snackbar.LENGTH_LONG).show()
    }
}