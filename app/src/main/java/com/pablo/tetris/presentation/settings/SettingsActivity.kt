package com.pablo.tetris.presentation.settings

import android.content.Intent
import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.pablo.tetris.databinding.ActivitySettingsBinding
import com.pablo.tetris.domain.game.Level
import com.pablo.tetris.presentation.common.GAME_INFORMATION
import com.pablo.tetris.presentation.common.HideStatusBarActivity
import com.pablo.tetris.presentation.game.GameActivity
import kotlinx.coroutines.flow.collect

class SettingsActivity : HideStatusBarActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var viewModel: SettingsModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpModelView()
        setUpComponents()
    }

    fun setUpModelView() {
        viewModel = ViewModelProvider(this).get(SettingsModel::class.java)
        lifecycleScope.launchWhenCreated {
            viewModel.results.collect {
                binding.editTextPersonName.error = it.nameError
                if (it.nameError == null) {
                    val intent = Intent(this@SettingsActivity, GameActivity::class.java).apply {
                        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                        putExtra(GAME_INFORMATION, it)
                    }
                    startActivity(intent)
                }
            }
        }
    }

    fun setUpComponents() {
        binding.editTextPersonName.addTextChangedListener { viewModel.update(DataValue.Name(it.toString())) }
        binding.CheckBoxGhostBlock.setOnClickListener { viewModel.update(DataValue.HasGhost(binding.CheckBoxGhostBlock.isChecked)) }
        binding.MusicCheckbox.setOnClickListener { viewModel.update(DataValue.HasMusic(binding.MusicCheckbox.isChecked)) }
        binding.StartButton.setOnClickListener { viewModel.collect() }
        binding.ThemeSpinner.adapter = Spinner.Adapter.get(this)
        binding.ThemeSpinner.onItemSelectedListener = Spinner(viewModel)
        binding.LowLevel.setOnClickListener { viewModel.update(DataValue.Level(Level.LOW)) }
        binding.MediumLevelButton.setOnClickListener { viewModel.update(DataValue.Level(Level.MEDIUM)) }
        binding.HighLevelButton.setOnClickListener { viewModel.update(DataValue.Level(Level.HIGH)) }
    }
}