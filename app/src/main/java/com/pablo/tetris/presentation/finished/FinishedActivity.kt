package com.pablo.tetris.presentation.finished

import android.os.Bundle
import com.pablo.tetris.databinding.ActivityFinishedBinding
import com.pablo.tetris.presentation.common.HideStatusBarActivity

class FinishedActivity : HideStatusBarActivity() {

    private lateinit var binding: ActivityFinishedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishedBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}