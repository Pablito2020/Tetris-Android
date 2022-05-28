package com.pablo.tetris.domain.user

import com.pablo.tetris.R
import com.pablo.tetris.presentation.common.UiText

class ValidateName(private val name: String): Validator {

    companion object {
        private const val MAX_LENGTH = 20
    }

    override fun execute(): ValidationResult {
        if (name.isBlank())
            return ValidationResult(false, UiText.ResourceString(R.string.error_name_is_empty))
        if (name.contains(" "))
            return ValidationResult(false, UiText.ResourceString(R.string.error_name_contain_spaces))
        if (name.contains("\n"))
            return ValidationResult(false, UiText.ResourceString(R.string.error_name_has_to_be_one_line))
        if (name.length > MAX_LENGTH)
            return ValidationResult(false, UiText.ResourceString(R.string.error_name_length_is_out_of_bounds))
        return ValidationResult(true)
    }

}