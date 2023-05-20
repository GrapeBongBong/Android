package com.example.android_bong.view.main.talentexchange.create

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class TalentExchangeCreateViewModel @Inject constructor(

) : ViewModel() {
    private val _uiState = MutableStateFlow(TalentExchangeCreateUiState())
    val uiState = _uiState.asStateFlow()
}