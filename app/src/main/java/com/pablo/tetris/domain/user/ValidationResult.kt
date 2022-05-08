package com.pablo.tetris.domain.user

import com.pablo.tetris.presentation.common.UiText

data class ValidationResult(
    val success: Boolean,
    val errorMessage: UiText?=null
)
