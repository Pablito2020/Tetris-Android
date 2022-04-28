package com.pablo.tetris.presentation.game

import GameFacade
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {

    val gameFacade: MutableLiveData<GameFacade> = MutableLiveData(null)

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

    fun setUp(gameFacade: GameFacade) {
        if (this.gameFacade.value == null) {
            gameFacade.start()
            this.gameFacade.value = gameFacade
        }
    }

}