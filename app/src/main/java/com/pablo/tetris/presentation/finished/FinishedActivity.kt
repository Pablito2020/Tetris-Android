package com.pablo.tetris.presentation.finished

import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.pablo.tetris.databinding.ActivityFinishedBinding
import com.pablo.tetris.infra.database.Player
import com.pablo.tetris.infra.database.PlayerApplication
import com.pablo.tetris.infra.logs.LoggerGetter
import com.pablo.tetris.presentation.common.GAME_RESULT
import com.pablo.tetris.presentation.common.HideStatusBarActivity
import com.pablo.tetris.presentation.finished.sendmail.EmailData
import com.pablo.tetris.presentation.finished.sendmail.EmailSender
import com.pablo.tetris.presentation.game.GameActivity
import com.pablo.tetris.presentation.game.results.DateGetter
import com.pablo.tetris.presentation.game.results.GameResult
import com.pablo.tetris.presentation.settings.SettingsFactory
import kotlinx.coroutines.flow.collect


class FinishedActivity : HideStatusBarActivity() {

    private lateinit var binding: ActivityFinishedBinding
    private val model: FinishedViewModel by lazy {
        ViewModelProvider(this, PlayerViewModelFactory((application as PlayerApplication).repository)).get(FinishedViewModel::class.java)
    }
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
        addResultToDataBase()
    }

    private fun setUpViewModel() {
        model.setUpLog(this)
        lifecycleScope.launchWhenCreated {
            model.results.collect {
                binding.emailEditText.error = it.emailError
                if (it.emailError == null) {
                    val data = EmailData(
                        destinationEmail = it.email,
                        text = getLogMessage(),
                        subject = DateGetter.getDate(gameResult.date)
                    )
                    EmailSender(this@FinishedActivity, data).send()
                }
            }
        }
        if (SettingsFactory().getSettingsData(this).hasMusic)
            model.playGameOverMusic(this)
    }

    private fun setUpComponents() {
        binding.logText.movementMethod = ScrollingMovementMethod()
        binding.logText.text = getLogMessage()
        binding.SendEmailButton.setOnClickListener { model.collect(this) }
        binding.emailEditText.addTextChangedListener { model.update(it.toString()) }
        binding.time.text = DateGetter.getDate(gameResult.date)
        binding.score.text = gameResult.score.toString()
        binding.ExitButton.setOnClickListener { onBackPressed() }
        binding.NewGameButton.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    GameActivity::class.java
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

    private fun addResultToDataBase() {
        val settings = SettingsFactory().getSettingsData(this)
        val player = Player(
            name = settings.name,
            score = gameResult.score,
            level = settings.level.name,
            date = DateGetter.getDate(gameResult.date),
            log = model.result.value!!
        )
        model.insert(player)
    }

    private fun getLogMessage() = model.result.value!!

}