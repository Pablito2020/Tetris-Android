package com.pablo.tetris.domain.user

data class ValidationResult(
    val success: Boolean,
    val errorMessage: String?=null
)
