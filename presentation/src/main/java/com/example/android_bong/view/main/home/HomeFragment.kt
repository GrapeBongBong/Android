package com.example.android_bong.view.main.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.android_bong.R
import com.example.android_bong.common.ViewBindingFragment
import com.example.android_bong.databinding.FragmentHomeBinding
import com.example.android_bong.view.main.MainViewModel
import com.example.android_bong.view.main.profile.ProfileUiState
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class HomeFragment : ViewBindingFragment<FragmentHomeBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    private val viewModel: MainViewModel by activityViewModels()
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.profileUiState.collect {
                    updateProfileUi(it)
                }
            }
        }
    }

    private fun updateProfileUi(uiState: ProfileUiState) = with(binding) {
        if (uiState.currentUser != null) {
            userNickname.text = getString(R.string.user_nickname, uiState.currentUser.nickName)
            userTemperature.text =
                getString(R.string.user_temperature, uiState.currentUser.temperature.toString())
        }

        if (uiState.userMessage != null) {
            showSnackBar(uiState.userMessage.toString())
            viewModel.userProfileMessageShown()
        }
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_LONG).show()
    }

}