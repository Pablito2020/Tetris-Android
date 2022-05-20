package com.pablo.tetris.presentation.game

import GameFacade
import android.content.Context
import android.media.MediaPlayer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pablo.tetris.R
import com.pablo.tetris.domain.game.speed.SpeedStrategy
import com.pablo.tetris.infra.logs.LoggerConstants
import com.pablo.tetris.infra.logs.LoggerGetter
import kotlinx.coroutines.delay

class GameViewModel : ViewModel() {

    val gameFacade: MutableLiveData<GameFacade> = MutableLiveData(null)
    private val lengthSong: MutableLiveData<Int> = MutableLiveData(0)
    private val song: MutableLiveData<MediaPlayer> = MutableLiveData(null)
    private val gamePaused: MutableLiveData<Boolean> = MutableLiveData(false)
    val gameOpened: MutableLiveData<Boolean> = MutableLiveData(false)
    val pauseButtonClicked: MutableLiveData<Boolean> = MutableLiveData(false)
    val pauseRotation: MutableLiveData<Boolean> = MutableLiveData(false)
    val updateScreen: MutableLiveData<Boolean> = MutableLiveData(false)
    private lateinit var speedStrategy: SpeedStrategy

    fun setUp(gameFacade: GameFacade, speed: SpeedStrategy) {
        if (this.gameFacade.value == null) {
            gameFacade.start()
            this.gameFacade.value = gameFacade
        }
        this.speedStrategy = speed
    }

    suspend fun runGame() {
        while (!gameFacade.value!!.hasFinished()) {
            down()
            delay(speedStrategy.getSpeedInMilliseconds(gameFacade.value!!.getScore()))
        }
    }

    fun left() {
        if (validMovement()) {
            gameFacade.value?.left()
            gameFacade.postValue(gameFacade.value)
            LoggerGetter.get().add(LoggerConstants.MOVE_LEFT)
        }
    }

    fun right() {
        if (validMovement()) {
            gameFacade.value?.right()
            gameFacade.postValue(gameFacade.value)
            LoggerGetter.get().add(LoggerConstants.MOVE_RIGHT)
        }
    }

    fun down() {
        if (validMovement()) {
            gameFacade.value?.down()
            gameFacade.postValue(gameFacade.value)
            LoggerGetter.get().add(LoggerConstants.MOVE_DOWN)
        }
    }

    fun rotateLeft() {
        if (validMovement()) {
            gameFacade.value?.rotateLeft()
            gameFacade.postValue(gameFacade.value)
            LoggerGetter.get().add(LoggerConstants.ROTATE_LEFT)
        }
    }

    fun rotateRight() {
        if (validMovement()) {
            gameFacade.value?.rotateRight()
            gameFacade.postValue(gameFacade.value)
            LoggerGetter.get().add(LoggerConstants.ROTATE_RIGHT)
        }
    }

    fun dropBlock() {
        if (validMovement()) {
            gameFacade.value?.dropBlock()
            gameFacade.postValue(gameFacade.value)
            LoggerGetter.get().add(LoggerConstants.DROP_DOWN)
        }
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
        if (song.value != null && song.value!!.isPlaying) {
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

    fun isGameStarted() = gameOpened.value!!

    fun setGameStarted() {
        gameOpened.value = true
    }

    fun isGamePaused() = gamePaused.value!!

    fun setGamePaused() {
        gamePaused.value = true
    }

    fun setGameResume() {
        gamePaused.value = false
    }

    private fun validMovement() = !gamePaused.value!! && !gameFacade.value!!.hasFinished()

}