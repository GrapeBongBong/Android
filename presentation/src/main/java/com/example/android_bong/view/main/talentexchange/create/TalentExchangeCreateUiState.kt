package com.example.android_bong.view.main.talentexchange.create

import android.net.Uri

data class TalentExchangeCreateUiState(
    val userMessage: String? = null,
    val title: String = "",
    val content: String = "",
    val giveCate: String = "",
    val takeCate: String = "",
    val giveTalent: String = "",
    val takeTalent: String = "",
    val possibleDays: MutableList<String> = mutableListOf(),
    val possibleTimeZone: String = "",
    val selectedImage: Uri? = null,
    val isSuccessPosting: Boolean = false,
    val isLoading: Boolean = false
) {
    val isInputValid: Boolean
        get() = isValidTitle && isValidContent && isValidGiveCate && isValidTakeCate && isValidGiveTalent && isValidTakeTalent

    private val isValidTitle: Boolean
        get() = title.isNotEmpty()

    private val isValidContent: Boolean
        get() = content.isNotEmpty()

    private val isValidGiveCate: Boolean
        get() = giveCate.isNotEmpty()

    private val isValidTakeCate: Boolean
        get() = takeCate.isNotEmpty()

    private val isValidGiveTalent: Boolean
        get() = giveTalent.isNotEmpty()

    private val isValidTakeTalent: Boolean
        get() = takeTalent.isNotEmpty()

    private val isValidDays: Boolean
        get() = possibleDays.isNotEmpty()

    private val isValidTimeZone: Boolean
        get() = possibleTimeZone.isNotEmpty()

}