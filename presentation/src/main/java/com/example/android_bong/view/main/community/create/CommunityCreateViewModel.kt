package com.example.android_bong.view.main.community.create

import android.graphics.Bitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.community.CreateCommunityPostUseCase
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
class CommunityCreateViewModel @Inject constructor(
    private val createCommunityPostUseCase: CreateCommunityPostUseCase,
    private val convertBitmapToFileUseCase: ConvertBitmapToFileUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(CommunityCreateUiState())
    val uiState = _uiState.asStateFlow()

    private var fetchJob: Job? = null

    fun updateTitle(title: String) {
        _uiState.update { it.copy(title = title) }
    }

    fun updateContent(content: String) {
        _uiState.update { it.copy(content = content) }
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
        val images = mutableListOf(uiState.value.image1, uiState.value.image2, uiState.value.image3)
        _uiState.update {
            it.copy(isLoading = true)
        }
        fetchJob?.cancel()
        fetchJob = viewModelScope.launch {
            val result = createCommunityPostUseCase(
                title = title,
                content = content,
                images = images.map {
                    val randomNumber = Random.nextInt(1, 11) // 5부터 9까지의 난수
                    it?.let {
                        convertBitmapToFileUseCase(it, "CommunityPostImage+${randomNumber}.jpg")
                    }
                }
            )
            if (result.isSuccess) {
                _uiState.update {
                    it.copy(
                        userMessage = result.getOrNull(), isSuccessPosting = true, isLoading = false
                    )
                }
            } else {
                _uiState.update {
                    it.copy(
                        userMessage = result.exceptionOrNull()!!.localizedMessage,
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