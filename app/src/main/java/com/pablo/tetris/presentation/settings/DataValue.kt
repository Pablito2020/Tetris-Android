package com.pablo.tetris.presentation.settings

sealed class DataValue {
    data class Name(val name: String) : DataValue()
    data class HasGhost(val hasGhost: Boolean) : DataValue()
    data class HasMusic(val hasMusic: Boolean) : DataValue()
    data class Theme(val themeIndex: Int) : DataValue()
}
