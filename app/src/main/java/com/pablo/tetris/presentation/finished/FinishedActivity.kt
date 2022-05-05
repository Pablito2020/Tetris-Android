package com.pablo.tetris.presentation.finished

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.pablo.tetris.databinding.ActivityFinishedBinding
import com.pablo.tetris.infra.logs.LoggerGetter
import com.pablo.tetris.presentation.common.GAME_INFORMATION
import com.pablo.tetris.presentation.common.GAME_RESULT
import com.pablo.tetris.presentation.common.HideStatusBarActivity
import com.pablo.tetris.presentation.finished.sendmail.EmailData
import com.pablo.tetris.presentation.finished.sendmail.EmailSender
import com.pablo.tetris.presentation.game.results.GameResult
import kotlinx.coroutines.flow.collect

class FinishedActivity : HideStatusBarActivity() {

    private lateinit var binding: ActivityFinishedBinding
    private lateinit var model: FinishedViewModel
    private lateinit var gameResult: GameResult

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFinishedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        intent.getSerializableExtra(GAME_RESULT)?.let {
            gameResult = it as GameResult
        }
        setUpViewModel()
        setUpComponents()
    }

    private fun setUpViewModel() {
        model = ViewModelProvider(this).get(FinishedViewModel::class.java)
        lifecycleScope.launchWhenCreated {
            model.results.collect {
                binding.emailEditText.error = it.emailError
                if (it.emailError == null) {
                    val data = EmailData(
                        destinationEmail = it.email,
                        text = getLogMessage(),
                        subject = gameResult.date
                    )
                    EmailSender(this@FinishedActivity, data).send()
                }
            }
        }
    }

    private fun setUpComponents() {
        binding.logText.movementMethod = ScrollingMovementMethod()
        binding.logText.text = LoggerGetter.get().getLog().joinToString(separator = "\n")
        binding.SendEmailButton.setOnClickListener { model.collect() }
        binding.emailEditText.addTextChangedListener { model.update(it.toString()) }
        binding.time.text = gameResult.date
        binding.score.text = gameResult.score
    }

    override fun onDestroy() {
        super.onDestroy()
        LoggerGetter.get().clear()
    }

    private fun getLogMessage() = LoggerGetter.get().getLog().joinToString(separator = "\n")

}