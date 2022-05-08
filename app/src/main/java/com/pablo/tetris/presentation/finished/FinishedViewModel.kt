package com.pablo.tetris.presentation.finished

import android.content.Context
import android.media.MediaPlayer
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pablo.tetris.R
import com.pablo.tetris.domain.user.ValidateEmail
import com.pablo.tetris.infra.logs.LoggerGetter
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class FinishedViewModel : ViewModel() {

    private var data by mutableStateOf(FinishedStateData())
    private val resultEventChannel = Channel<FinishedStateData>()
    private val gameOverMusicHasPlayed = mutableStateOf(false)
    val results = resultEventChannel.receiveAsFlow()
    val result: MutableState<String?> = mutableStateOf(null)

    fun update(email: String) {
        this.data = data.copy(email = email)
    }

    fun setUpLog(context: Context) {
        if (result.value == null)
            result.value = LoggerGetter.get().getLog(context).joinToString(separator = "\n")
    }

    fun collect() {
        val result = ValidateEmail(data.email).execute()
        data = if (result.success)
            data.copy(emailError = null)
        else
            data.copy(emailError = result.errorMessage)
        viewModelScope.launch { resultEventChannel.send(data) }
    }

    fun playGameOverMusic(context: Context) {
        if (!gameOverMusicHasPlayed.value) {
            val mediaPlayer = MediaPlayer.create(context, R.raw.tetrisgameover)
            mediaPlayer.start()
            gameOverMusicHasPlayed.value = true
        }
    }

}