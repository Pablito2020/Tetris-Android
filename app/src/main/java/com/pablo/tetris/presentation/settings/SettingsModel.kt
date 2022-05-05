package com.pablo.tetris.presentation.settings

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pablo.tetris.domain.user.ValidateName
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SettingsModel: ViewModel() {

    private var state by mutableStateOf(SettingsData())
    private val resultEventChannel = Channel<SettingsData>()
    val results = resultEventChannel.receiveAsFlow()

    fun update(value: DataValue) {
        when(value) {
            is DataValue.Name -> {
                state = state.copy(name = value.name)
            }
            is DataValue.HasMusic -> {
                state = state.copy(hasMusic = value.hasMusic)
            }
            is DataValue.HasGhost -> {
                state = state.copy(isGhostBlock = value.hasGhost)
            }
            is DataValue.Theme -> {
                state = state.copy(themeIndex = value.themeIndex)
            }
            is DataValue.Level -> {
                state = state.copy(level = value.level)
            }
        }
    }

    fun collect() {
        val nameResult = ValidateName(state.name).execute()
        state = if (!nameResult.success)
            state.copy(nameError = nameResult.errorMessage)
        else
            state.copy(nameError = null)
        viewModelScope.launch {
            resultEventChannel.send(state)
        }
    }

}