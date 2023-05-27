package com.example.android_bong.view.main.community.edit

import androidx.lifecycle.ViewModel
import com.example.domain.usecase.community.UpdateCommunityPostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CommunityEditViewModel @Inject constructor(
    private val updateCommunityPostUseCase: UpdateCommunityPostUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CommunityEditUiState())
    val uiState = _uiState.asStateFlow()


    fun bind() {

    }
}