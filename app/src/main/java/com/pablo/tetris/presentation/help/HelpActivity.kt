package com.pablo.tetris.presentation.help

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.pablo.tetris.R
import com.pablo.tetris.databinding.ActivityHelpBinding
import com.pablo.tetris.presentation.common.HideStatusBarActivity
import com.pablo.tetris.presentation.main.MainActivity


class HelpActivity : HideStatusBarActivity() {

    private lateinit var binding: ActivityHelpBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHelpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpTetrisBlockVideo()
        setUpBackButton()
    }

    private fun setUpTetrisBlockVideo() {
        val uri = Uri.parse("android.resource://" + packageName + "/" + R.raw.tetris_blocks)
        binding.TetrisVideo.setVideoURI(uri)
        binding.TetrisVideo.setOnPreparedListener { it.isLooping = true }
        binding.TetrisVideo.start()
    }

    private fun setUpBackButton() {
        binding.BackButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}