package com.pablo.tetris.presentation.game.actions

import android.content.Context
import android.widget.Toast
import com.pablo.tetris.R

class ResumeToastAction(private val context: Context) : Action {

    override fun execute() {
        Toast.makeText(context, R.string.resumingin, Toast.LENGTH_LONG).show()
    }

}