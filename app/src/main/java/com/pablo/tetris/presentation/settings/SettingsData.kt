package com.pablo.tetris.presentation.settings

import com.pablo.tetris.domain.game.Level
import com.pablo.tetris.presentation.game.grid.style.Style

data class SettingsData(
    val name: String = "",
    val isGhostBlock: Boolean = true,
    val hasMusic: Boolean = true,
    val style: Style = Style.NEON,
    val level: Level = Level.MEDIUM,
)