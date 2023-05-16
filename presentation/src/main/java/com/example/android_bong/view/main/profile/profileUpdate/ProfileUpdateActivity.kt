package com.example.android_bong.view.main.profile.profileUpdate

import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.android_bong.common.ViewBindingActivity
import com.example.android_bong.databinding.ActivityProfileUpdateBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileUpdateActivity : ViewBindingActivity<ActivityProfileUpdateBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivityProfileUpdateBinding
        get() = ActivityProfileUpdateBinding::inflate

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
            }
        }
    }

    private fun updateUi() {

    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }


}