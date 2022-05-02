package com.pablo.tetris.presentation.game

import GameFacade
import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pablo.tetris.R
import kotlinx.coroutines.delay

internal const val BLOCK_DOWN_MILLISECONDS = 1000L

class GameViewModel : ViewModel() {

    val gameFacade: MutableLiveData<GameFacade> = MutableLiveData(null)
    private val lengthSong: MutableLiveData<Int> = MutableLiveData(0)
    val song: MutableLiveData<MediaPlayer> = MutableLiveData(null)

    fun setUp(gameFacade: GameFacade) {
        if (this.gameFacade.value == null) {
            gameFacade.start()
            this.gameFacade.value = gameFacade
        }
    }

    suspend fun run() {
        while (!gameFacade.value!!.hasFinished()) {
            down()
            delay(BLOCK_DOWN_MILLISECONDS)
        }
    }

    fun left() {
        gameFacade.value?.left()
        gameFacade.postValue(gameFacade.value)
    }

    fun right() {
        gameFacade.value?.right()
        gameFacade.postValue(gameFacade.value)
    }

    fun down() {
        gameFacade.value?.down()
        gameFacade.postValue(gameFacade.value)
    }

    fun rotateLeft() {
        gameFacade.value?.rotateLeft()
        gameFacade.postValue(gameFacade.value)
    }

    fun rotateRight() {
        gameFacade.value?.rotateRight()
        gameFacade.postValue(gameFacade.value)
    }

    fun dropBlock() {
        gameFacade.value?.dropBlock()
        gameFacade.postValue(gameFacade.value)
    }

    fun getGrid() = gameFacade.value!!.getGrid().flatMap { it.toList() }

    fun getNextBlock() = gameFacade.value!!.getNextBlock()

    fun getPoints() = gameFacade.value!!.getScore().value.toString()

    fun setUpMusic(hasMusic: Boolean, context: Context) {
        if (hasMusic) {
            song.value = MediaPlayer.create(context, R.raw.tetristheme)
            song.value?.isLooping = true
        }
    }

    fun pauseMusic() {
        if (song.value != null) {
            song.value?.pause()
            lengthSong.value = song.value?.currentPosition
        }
    }

    fun startMusic() {
        if (song.value != null) {
            song.value?.seekTo(lengthSong.value!!)
            song.value?.start()
        }
    }

}