package com.pablo.tetris.presentation.settings

import java.io.Serializable
import com.pablo.tetris.domain.game.Level

data class SettingsData(
    val name: String = "",
    val nameError: String? = null,
    val isGhostBlock: Boolean = true,
    val hasMusic: Boolean = true,
    val themeIndex: Int = 0,
    val level: Level = Level.MEDIUM,
): Serializable
