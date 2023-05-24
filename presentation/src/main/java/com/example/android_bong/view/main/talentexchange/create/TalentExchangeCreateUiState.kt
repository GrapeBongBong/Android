package com.example.android_bong.view.main.talentexchange.create

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
    val isSuccessPosting: Boolean = false
) {
    val isInputValid: Boolean
        get() = isValidTitle && isValidContent && isValidGiveCate && isValidTakeCate && isValidGiveTalent && isValidTakeTalent && isValidDays && isValidTimeZone

    private val isValidTitle: Boolean
        get() = title.length >= 0

    private val isValidContent: Boolean
        get() = content.length >= 0

    private val isValidGiveCate: Boolean
        get() = giveCate.length >= 0

    private val isValidTakeCate: Boolean
        get() = takeCate.length >= 0

    private val isValidGiveTalent: Boolean
        get() = giveTalent.length >= 0

    private val isValidTakeTalent: Boolean
        get() = takeTalent.length >= 0

    private val isValidDays: Boolean
        get() = possibleDays.isNotEmpty()

    private val isValidTimeZone: Boolean
        get() = possibleTimeZone.length >= 0

}