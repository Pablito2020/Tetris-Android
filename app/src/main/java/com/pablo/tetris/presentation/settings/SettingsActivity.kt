package com.pablo.tetris.presentation.settings

import android.os.Bundle
import com.pablo.tetris.databinding.ActivitySettingsBinding
import com.pablo.tetris.presentation.common.HideStatusBarActivity

class SettingsActivity : HideStatusBarActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}