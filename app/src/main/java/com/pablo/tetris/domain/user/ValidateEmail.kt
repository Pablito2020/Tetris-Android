package com.pablo.tetris.domain.user

import android.util.Patterns

class ValidateEmail(private val email: String): Validator {

    override fun execute(): ValidationResult {
        if(email.isBlank())
            return ValidationResult(false, "Email cannot be blank")
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return ValidationResult(false, "Email is not valid")
        return ValidationResult(true)
    }

}