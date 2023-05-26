package com.example.android_bong.view.main.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.android_bong.R
import com.example.android_bong.common.ViewBindingFragment
import com.example.android_bong.databinding.FragmentProfileBinding
import com.example.android_bong.extension.RefreshStateContract
import com.example.android_bong.view.main.MainViewModel
import com.example.android_bong.view.main.profile.profileUpdate.ProfileUpdateActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class ProfileFragment : ViewBindingFragment<FragmentProfileBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProfileBinding
        get() = FragmentProfileBinding::inflate

    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var launcher: ActivityResultLauncher<Intent>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.profileUiState.collect {
                    updateUi(it)
                }
            }
        }

        launcher = registerForActivityResult(RefreshStateContract()) {
            if (it != null) {
                viewModel.fetchUserTemperature()
                it.message?.let { message -> showSnackBar(message) }
            }
        }
    }

    private fun updateUi(uiState: ProfileUiState) = with(binding) {

        if (uiState.currentUser != null) {
            val user = uiState.currentUser
            nicknameText.text = getString(R.string.profile_userNickName, user.nickName)
            userImage

            profileTemperature.text =
                getString(R.string.profile_temperature, user.temperature.toString())
        }

        if (uiState.userMessage != null) {
            showSnackBar(getString(uiState.userMessage))
            viewModel.userProfileMessageShown()
        }
        updatingButton.setOnClickListener {
            navigateToProfileUpdateActivity()
        }

    }

    private fun navigateToProfileUpdateActivity() {
        val intent = ProfileUpdateActivity.getIntent(requireContext())
        launcher.launch(intent)
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

}