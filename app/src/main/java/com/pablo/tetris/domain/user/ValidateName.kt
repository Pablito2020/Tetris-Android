package com.pablo.tetris.domain.user

import com.pablo.tetris.R
import com.pablo.tetris.presentation.common.UiText

class ValidateName(private val name: String): Validator {

    override fun execute(): ValidationResult {
        if (name.isBlank())
            return ValidationResult(false, UiText.ResourceString(R.string.error_name_is_empty))
        if (name.contains(" "))
            return ValidationResult(false, UiText.ResourceString(R.string.error_name_contain_spaces))
        return ValidationResult(true)
    }

}