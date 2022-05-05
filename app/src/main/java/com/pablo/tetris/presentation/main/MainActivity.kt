package com.pablo.tetris.presentation.main

import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.os.Bundle
import android.view.View
import com.pablo.tetris.databinding.ActivityMainBinding
import com.pablo.tetris.presentation.common.HideStatusBarActivity
import com.pablo.tetris.presentation.getButtons
import com.pablo.tetris.presentation.settings.SettingsActivity

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
            binding.startButton.id -> startSettingsActivity()
            else -> TODO("More buttons need to go here")
        }
    }

    private fun startSettingsActivity() {
        val game = Intent(this, SettingsActivity::class.java).apply {
            addFlags(FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK)
        }
        startActivity(game)
    }

}