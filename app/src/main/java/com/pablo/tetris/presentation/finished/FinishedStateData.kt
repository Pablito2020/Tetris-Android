package com.pablo.tetris.presentation.finished

import java.io.Serializable

data class FinishedStateData(
    val email: String = "",
    val emailError: String? = null
): Serializable