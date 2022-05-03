package com.pablo.tetris.presentation.game.toast

import android.content.Context
import android.os.CountDownTimer
import android.widget.Toast

internal const val toastDurationInMilliSeconds = 1000L

/*
Creates a toast with a custom duration (1 second, instead of the default 2.5 that
 the Toast.LENGTH_SHORT does.
 */
fun oneSecondToast(context: Context, string: String) {
    val toast = Toast.makeText(context, string, Toast.LENGTH_SHORT)
    val toastCountDown = object : CountDownTimer(toastDurationInMilliSeconds, toastDurationInMilliSeconds) {
        override fun onTick(millisUntilFinished: Long) {
            toast.show()
        }
        override fun onFinish() {
            toast.cancel()
        }
    }
    toast.show()
    toastCountDown.start()
}