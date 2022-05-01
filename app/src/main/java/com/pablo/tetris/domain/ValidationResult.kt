package com.pablo.tetris.domain

data class ValidationResult(
    val success: Boolean,
    val errorMessage: String?=null
)
