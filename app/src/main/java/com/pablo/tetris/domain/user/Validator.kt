package com.pablo.tetris.domain.user

interface Validator {
    fun execute(): ValidationResult
}