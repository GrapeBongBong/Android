package com.example.android_bong.view.main.talentexchange.detail

import androidx.lifecycle.ViewModel
import com.example.android_bong.view.main.talentexchange.TalentExchangeItemUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class TalentExchangeDetailViewModel @Inject constructor(

) : ViewModel() {

    private val _uiState = MutableStateFlow(TalentExchangeDetailUiState())
    val uiState = _uiState.asStateFlow()

    fun bind(item: TalentExchangeItemUiState) {
        _uiState.update {
            it.copy(item = item)
        }
    }

}