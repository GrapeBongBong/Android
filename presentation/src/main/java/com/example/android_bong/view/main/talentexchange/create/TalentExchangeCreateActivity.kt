package com.example.android_bong.view.main.talentexchange.create

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.android_bong.R
import com.example.android_bong.common.GlideApp
import com.example.android_bong.common.ViewBindingActivity
import com.example.android_bong.databinding.ActivityTalentExchangeCreateBinding
import com.example.android_bong.extension.setResultRefresh
import com.example.android_bong.extension.toBitmap
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

    private val pickMedia1 =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { imageUri ->
            if (imageUri != null) {
                viewModel.updateImages1(imageUri.toBitmap(this))
            }
            val glide = GlideApp.with(this)
            glide.load(imageUri)
                .into(binding.imageView1)
        }

    private val pickMedia2 =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { imageUri ->
            if (imageUri != null) {
                viewModel.updateImages2(imageUri.toBitmap(this))
            }
            Log.d("imageUri2", imageUri.toString())
            val glide = GlideApp.with(this)
            glide.load(imageUri)
                .into(binding.imageView2)
        }

    private val pickMedia3 =
        registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { imageUri ->
            if (imageUri != null) {
                viewModel.updateImages3(imageUri.toBitmap(this))
            }
            Log.d("imageUri3", imageUri.toString())
            val glide = GlideApp.with(this)
            glide.load(imageUri)
                .into(binding.imageView3)
        }

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
            isEnabled = uiState.isInputValid && !uiState.isLoading
            setText(if (uiState.isLoading) R.string.loading else R.string.posting)
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

        dayGroup.setOnCheckedChangeListener { group, checkedId ->
            when (group.id) {
                R.id.dayGroup ->
                    when (checkedId) {
                        R.id.morning_button -> viewModel.updatePossibleDay(getString(R.string.monday))
                        R.id.tuesday_button -> viewModel.updatePossibleDay(getString(R.string.tuesday))
                        R.id.wednesday_button -> viewModel.updatePossibleDay(getString(R.string.wednesday))
                        R.id.thursday_button -> viewModel.updatePossibleDay(getString(R.string.thursday))
                        R.id.friday_button -> viewModel.updatePossibleDay(getString(R.string.friday))
                        R.id.saturday_button -> viewModel.updatePossibleDay(getString(R.string.saturday))
                        R.id.sunday_button -> viewModel.updatePossibleDay(getString(R.string.sunday))
                    }
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

        imageView1.setOnClickListener {
            showImagePicker1()
        }

        imageView2.setOnClickListener {
            showImagePicker2()
        }

        imageView3.setOnClickListener {
            showImagePicker3()
        }

    }

    private fun showImagePicker1() {
        pickMedia1.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun showImagePicker2() {
        pickMedia2.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun showImagePicker3() {
        pickMedia3.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(this, binding.root, message, Snackbar.LENGTH_LONG).show()
    }
}