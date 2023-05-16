package com.example.android_bong.view.main.talentexchange

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.android_bong.R
import com.example.android_bong.common.ViewBindingFragment
import com.example.android_bong.databinding.FragmentTalentExchangeBinding
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import kotlinx.coroutines.launch

class TalentExchangeFragment : ViewBindingFragment<FragmentTalentExchangeBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentTalentExchangeBinding
        get() = FragmentTalentExchangeBinding::inflate

    private val viewModel: TalentExchangeViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    updateUi(it)
                }
            }
        }
    }

    private fun onClickPost(talentExchangeItemUiState : TalentExchangeItemUiState){

    }

    private fun updateUi(uiState: TalentExchangeUiState) {
        val extendedFab = requireActivity().findViewById<ExtendedFloatingActionButton>(R.id.fab)
        extendedFab.isVisible = true
    }

}