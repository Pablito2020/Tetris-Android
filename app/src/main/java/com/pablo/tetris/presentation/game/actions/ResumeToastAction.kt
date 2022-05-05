package com.pablo.tetris.presentation.game.actions

import android.content.Context
import com.pablo.tetris.R
import com.pablo.tetris.presentation.game.toast.oneSecondToast

class ResumeToastAction(private val context: Context): Action {

    override fun execute() {
        val millisecondsDelay = 1500L
        val waitingSeconds = 3
        (waitingSeconds downTo 1).forEach { displayToast(it); Thread.sleep(millisecondsDelay) }
    }

    private fun displayToast(iteration: Int) {
        oneSecondToast(context, "${context.getString(R.string.resumingin)} $iteration")
    }

}