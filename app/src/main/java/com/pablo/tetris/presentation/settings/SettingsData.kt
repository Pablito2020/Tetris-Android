package com.pablo.tetris.presentation.settings

data class SettingsData(
    val name: String = "",
    val nameError: String? = null,
    val isGhostBlock : Boolean = true,
    val hasMusic: Boolean = true,
)
