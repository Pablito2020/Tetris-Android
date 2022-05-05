package com.pablo.tetris.presentation.finished

import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.pablo.tetris.R
import com.pablo.tetris.databinding.ActivityFinishedBinding
import com.pablo.tetris.infra.logs.LoggerGetter
import com.pablo.tetris.presentation.common.GAME_RESULT
import com.pablo.tetris.presentation.common.HideStatusBarActivity
import com.pablo.tetris.presentation.finished.sendmail.EmailData
import com.pablo.tetris.presentation.finished.sendmail.EmailSender
import com.pablo.tetris.presentation.game.results.GameResult
import com.pablo.tetris.presentation.settings.SettingsActivity
import kotlinx.coroutines.flow.collect
import kotlin.system.exitProcess


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
        binding.ExitButton.setOnClickListener { onBackPressed() }
        binding.NewGameButton.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    SettingsActivity::class.java
                ).apply {
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                }
            )
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        LoggerGetter.get().clear()
    }

    private fun getLogMessage() = LoggerGetter.get().getLog().joinToString(separator = "\n")

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setMessage(R.string.quitAppMessage)
            .setNegativeButton(R.string.cancel) { _, _ -> }
            .setPositiveButton(R.string.ok) { _, _ -> finishAffinity();exitProcess(0) }
            .create()
            .show()
    }

}