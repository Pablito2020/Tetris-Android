package com.pablo.tetris.domain.user

class ValidateName(private val name: String): Validator {

    override fun execute(): ValidationResult {
        if (name.isBlank())
            return ValidationResult(false, "Name cannot be empty")
        if (name.contains(" "))
            return ValidationResult(false, "Name cannot include spaces")
        return ValidationResult(true)
    }

}