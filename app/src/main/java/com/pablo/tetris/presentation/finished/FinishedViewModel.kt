package com.pablo.tetris.presentation.finished

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pablo.tetris.domain.user.ValidateEmail
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class FinishedViewModel : ViewModel() {

    private var data by mutableStateOf(FinishedStateData())
    private val resultEventChannel = Channel<FinishedStateData>()
    val results = resultEventChannel.receiveAsFlow()

    fun update(email: String) {
        this.data = data.copy(email = email)
    }

    fun collect() {
        val result = ValidateEmail(data.email).execute()
        data = if (result.success)
            data.copy(emailError = null)
        else
            data.copy(emailError = result.errorMessage)
        viewModelScope.launch { resultEventChannel.send(data) }
    }

}