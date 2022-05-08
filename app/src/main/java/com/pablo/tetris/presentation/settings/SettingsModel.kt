package com.pablo.tetris.presentation.settings

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pablo.tetris.R
import com.pablo.tetris.domain.game.Level
import com.pablo.tetris.domain.user.ValidateName
import com.pablo.tetris.presentation.game.grid.style.Style
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class SettingsModel: ViewModel() {

    private var state by mutableStateOf(SettingsData())
    private val resultEventChannel = Channel<SettingsData>()
    val results = resultEventChannel.receiveAsFlow()
    val uiChanged: MutableLiveData<UIChange> = MutableLiveData(null)

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
                uiChanged.postValue(UIChange.SPINNER_THEME)
            }
            is DataValue.Level -> {
                state = state.copy(level = value.level)
                uiChanged.postValue(UIChange.BUTTON_LEVEL)
            }
        }
    }

    fun collect(context: Context) {
        val nameResult = ValidateName(state.name).execute()
        state = if (!nameResult.success)
            state.copy(nameError = nameResult.errorMessage!!.asString(context))
        else
            state.copy(nameError = null)
        viewModelScope.launch {
            resultEventChannel.send(state)
        }
    }

    fun getImageLevelResource() = when(state.level) {
        Level.LOW -> R.drawable.easyicon
        Level.MEDIUM -> R.drawable.mediumicon
        Level.HIGH -> R.drawable.highicon
    }

    fun getStyleImageResource() = when(Style.values()[state.themeIndex]) {
        Style.NEON -> R.drawable.tblockneon
        Style.SATURATED -> R.drawable.tblocksaturated
    }

}