package com.example.android_bong.view.main.talentexchange.edit

import androidx.lifecycle.ViewModel
import com.example.domain.usecase.post.UpdateExchangePostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TalentExchangeEditViewModel @Inject constructor(
    updateExchangePostUseCase: UpdateExchangePostUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(TalentExchangeEditUiState())
    val uiState = _uiState.asStateFlow()
}