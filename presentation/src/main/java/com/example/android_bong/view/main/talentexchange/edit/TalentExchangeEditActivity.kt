package com.example.android_bong.view.main.talentexchange.edit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
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
import com.example.android_bong.databinding.ActivityTalentExchangeEditBinding
import com.example.android_bong.extension.setResultRefresh
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TalentExchangeEditActivity : ViewBindingActivity<ActivityTalentExchangeEditBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityTalentExchangeEditBinding
        get() = ActivityTalentExchangeEditBinding::inflate

    private val viewModel: TalentExchangeEditViewModel by viewModels()

    companion object {
        fun getIntent(
            context: Context,
            postId: Int,
            title: String,
            content: String,
            giveCate: String,
            takeCate: String,
            giveTalent: String,
            takeTalent: String,
            possibleTimeZone: String
        ): Intent {
            return Intent(context, TalentExchangeEditActivity::class.java).apply {
                putExtra("postId", postId)
                putExtra("title", title)
                putExtra("content", content)
                putExtra("giveCate", giveCate)
                putExtra("takeCate", takeCate)
                putExtra("giveTalent", giveTalent)
                putExtra("takeTalent", takeTalent)
                putExtra("possibleTimeZone", possibleTimeZone)
            }
        }
    }

    private fun getPostId(): Int {
        return intent.getIntExtra("postId", 0)
    }

    private fun getPostTitle(): String {
        return intent.getStringExtra("title")!!
    }

    private fun getPostContent(): String {
        return intent.getStringExtra("content")!!
    }

    private fun getPostGiveCate(): String {
        return intent.getStringExtra("giveCate")!!
    }

    private fun getPostTakeCate(): String {
        return intent.getStringExtra("takeCate")!!
    }

    private fun getPostGiveTalent(): String {
        return intent.getStringExtra("giveTalent")!!
    }

    private fun getPostTakeTalent(): String {
        return intent.getStringExtra("takeTalent")!!
    }

    private fun getPostPossibleTimeZone(): String {
        return intent.getStringExtra("possibleTimeZone")!!
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.toolbar.setTitle(R.string.exchange_updating)
        setSupportActionBar(binding.toolbar)
        val ab = supportActionBar!!
        ab.setDisplayHomeAsUpEnabled(true)
        viewModel.bind(getPostId())

        initEvent()
        initSetting(
            title = getPostTitle(),
            content = getPostContent(),
            giveCate = getPostGiveCate(),
            takeCate = getPostTakeCate(),
            giveTalent = getPostGiveTalent(),
            takeTalent = getPostTakeTalent(),
            possibleTimeZone = getPostPossibleTimeZone(),
        )

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
                setResultRefresh()
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        setResultRefresh()
        return super.onKeyDown(keyCode, event)
    }

    private fun initSetting(
        title: String,
        content: String,
        giveCate: String,
        takeCate: String,
        giveTalent: String,
        takeTalent: String,
        possibleTimeZone: String
    ) = with(binding) {

        binding.title.setText(title)
        binding.content.setText(content)

        binding.giveText.setText(giveTalent)
        binding.takeText.setText(takeTalent)


        when (possibleTimeZone) {
            getString(R.string.dawn) -> dawnButton.isChecked = true
            getString(R.string.morning) -> morningButton.isChecked = true
            getString(R.string.launch) -> lunchButton.isChecked = true
            getString(R.string.afternoon) -> afternoonButton.isChecked = true
            getString(R.string.dinner) -> dinnerButton.isChecked = true
        }

        viewModel.updateContent(title)
        viewModel.updateContent(content)
        viewModel.updateGiveCate(giveCate)
        viewModel.updateTakeCate(takeCate)
        viewModel.updateGiveTalent(giveTalent)
        viewModel.updateTakeTalent(takeTalent)


        viewModel.updatePossibleTime(possibleTimeZone)
    }

    private fun updateUi(uiState: TalentExchangeEditUiState) = with(binding) {

        if (uiState.userMessage != null) {
            showSnackBar(uiState.userMessage)
            viewModel.userMessageShown()
        }

        if (uiState.isSuccessUpdating) {
            setResultRefresh()
            finish()
        }

        updateButton.apply {
            isEnabled = uiState.isInputValid && !uiState.isLoading
            setText(if (uiState.isLoading) R.string.loading else R.string.doing_update)
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

        saturdayButton.setOnCheckedChangeListener { _, isChecked ->
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
                viewModel.updateImPossibleDay(getString(R.string.sunday))
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
            this@TalentExchangeEditActivity,
            R.array.talent_array,
            android.R.layout.simple_spinner_item
        )

        takeSpinner.adapter = ArrayAdapter.createFromResource(
            this@TalentExchangeEditActivity,
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

        updateButton.setOnClickListener {
            viewModel.updatePost()
        }

    }

    private fun showSnackBar(message: String) {
        Snackbar.make(this, binding.root, message, Snackbar.LENGTH_LONG).show()
    }
}