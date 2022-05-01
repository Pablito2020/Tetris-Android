package com.pablo.tetris.domain

class ValidateName {

    fun execute(name: String): ValidationResult {
        if (name.isBlank())
            return ValidationResult(false, "Name cannot be empty")
        if (name.contains(" "))
            return ValidationResult(false, "Name cannot include spaces")
        return ValidationResult(true)
    }

}