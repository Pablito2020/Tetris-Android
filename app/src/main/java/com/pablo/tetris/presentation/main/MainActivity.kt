package com.pablo.tetris.presentation.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.pablo.tetris.databinding.ActivityMainBinding
import com.pablo.tetris.presentation.common.HideStatusBarActivity
import com.pablo.tetris.presentation.game.GameActivity
import com.pablo.tetris.presentation.getButtons

class MainActivity : HideStatusBarActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.root.getButtons().forEach { it.setOnClickListener(this) }
    }

    override fun onClick(p0: View) {
        when (p0.id) {
            binding.startButton.id -> startGameActivity()
            else -> TODO("More buttons need to go here")
        }
    }

    private fun startGameActivity() {
        val game = Intent(this, GameActivity::class.java)
        startActivity(game)
    }

}