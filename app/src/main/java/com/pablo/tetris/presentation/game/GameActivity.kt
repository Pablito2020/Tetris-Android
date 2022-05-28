package com.pablo.tetris.presentation.game

import android.os.Bundle
import com.pablo.tetris.R
import com.pablo.tetris.presentation.common.HideStatusBarActivity


class GameActivity : HideStatusBarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
    }

}