package com.example.android_bong.view.main.talentexchange.create

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.post.CreateExchangePostUseCase
import com.example.domain.usecase.user.ConvertBitmapToFileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class TalentExchangeCreateViewModel @Inject constructor(
    private val createExchangePostUseCase: CreateExchangePostUseCase,
    private val convertBitmapToFileUseCase: ConvertBitmapToFileUseCase
) : ViewModel() {
    private val _uiState = MutableStateFlow(TalentExchangeCreateUiState())
    val uiState = _uiState.asStateFlow()

    private var fetchJob: Job? = null

    fun updateTitle(title: String) {
        _uiState.update { it.copy(title = title) }
    }

    fun updateContent(content: String) {
        _uiState.update { it.copy(content = content) }
    }

    fun updateGiveCate(giveCate: String) {
        _uiState.update { it.copy(giveCate = giveCate) }
    }

    fun updateTakeCate(takeCate: String) {
        _uiState.update { it.copy(takeCate = takeCate) }
    }

    fun updateGiveTalent(giveTalent: String) {
        _uiState.update { it.copy(giveTalent = giveTalent) }
    }

    fun updateTakeTalent(takeTalent: String) {
        _uiState.update { it.copy(takeTalent = takeTalent) }
    }

    fun updatePossibleDay(day: String) {
        _uiState.value.possibleDays.add(day)
    }

    fun updateImPossibleDay(day: String) {
        _uiState.value.possibleDays.remove(day)
    }

    fun updatePossibleTime(time: String) {
        _uiState.update {
            it.copy(possibleTimeZone = time)
        }
    }

    fun updateImages1(image: Bitmap) {
        _uiState.update {
            it.copy(image1 = image)
        }
    }

    fun updateImages2(image: Bitmap) {
        _uiState.update {
            it.copy(image2 = image)
        }
    }

    fun updateImages3(image: Bitmap) {
        _uiState.update {
            it.copy(image3 = image)
        }
    }

    fun createPost() {
        val title = uiState.value.title
        val content = uiState.value.content
        val giveCate = uiState.value.giveCate
        val takeCate = uiState.value.takeCate
        val giveTalent = uiState.value.giveTalent
        val takeTalent = uiState.value.takeTalent
        val days = uiState.value.possibleDays
        val timeZone = uiState.value.possibleTimeZone
        val images = mutableListOf(uiState.value.image1, uiState.value.image2, uiState.value.image3)
        _uiState.update {
            it.copy(isLoading = true)
        }
        Log.d("images", images.toString())
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            val result = createExchangePostUseCase(
                title = title,
                content = content,
                giveCate = giveCate,
                takeCate = takeCate,
                giveTalent = giveTalent,
                takeTalent = takeTalent,
                days = days,
                timeZone = timeZone,
                images = images.map {
                    val randomNumber = Random.nextInt(1, 1000)
                    it?.let {
                        convertBitmapToFileUseCase(it, "ExchangePostImage+${randomNumber}.jpg")
                    }
                }
            )
            if (result.isSuccess) {
                _uiState.update {
                    it.copy(
                        userMessage = "게시물이 생성되었습니다.",
                        isLoading = false,
                        isSuccessPosting = true
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        userMessage = result.exceptionOrNull()?.localizedMessage,
                        isLoading = false
                    )
                }
            }
        }
    }

    fun userMessageShown() {
        _uiState.update { it.copy(userMessage = null) }
    }

}