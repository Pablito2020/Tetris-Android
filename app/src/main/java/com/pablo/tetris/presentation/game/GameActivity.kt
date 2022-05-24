package com.pablo.tetris.presentation.game

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.pablo.tetris.R
import com.pablo.tetris.presentation.common.GAME_RESULT
import com.pablo.tetris.presentation.common.HideStatusBarActivity
import com.pablo.tetris.presentation.finished.FinishedActivity
import com.pablo.tetris.presentation.game.actions.Action
import com.pablo.tetris.presentation.game.actions.ResumeToastAction
import com.pablo.tetris.presentation.game.fragments.GameFragment
import com.pablo.tetris.presentation.game.fragments.LogFragment
import com.pablo.tetris.presentation.game.results.GameResult
import com.pablo.tetris.presentation.settings.SettingsFactory
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.*


@ExperimentalCoroutinesApi
class GameActivity : HideStatusBarActivity() {

    private lateinit var model: GameViewModel
    private lateinit var resumeAction: Action
    private lateinit var moveBlockDown: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        setUpViewModel()
        setUpResumeAction()
        setUpLogger()
        setUpObserver()
        setUpFragments()
    }

    private fun setUpViewModel() {
        model = ViewModelProvider(this).get(GameViewModel::class.java)
        model.setUp(SettingsFactory.getFacade(this), SettingsFactory.getSpeedStrategy(this))
        model.setUpMusic(SettingsFactory.getSettingsData(this).hasMusic, this)
        model.gameFacade.observe(this) {
            if (!it.hasFinished())
                model.updateScreen.value = true
            else
                finishGame()
        }
    }

    private fun setUpObserver() {
        model.pauseButtonClicked.observe(this) {
            if (it) {
                pauseButtonClicked()
                model.pauseButtonClicked.value = false
            }
        }
    }

    private fun setUpFragments() {
        val gameFragment = GameFragment()
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainerView, gameFragment)
            commit()
        }
        val logFragmentReference = findViewById<FrameLayout>(R.id.LogFragment)
        if (logFragmentReference != null) {
            val logFragment = LogFragment()
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.LogFragment, logFragment)
                commit()
            }
        }
    }

    private fun setUpLogger() {
        SettingsFactory.logData(this)
    }

    private fun setUpResumeAction() {
        resumeAction = ResumeToastAction(this)
    }

    private fun finishGame() {
        val finish = Intent(this@GameActivity, FinishedActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            putExtra(
                GAME_RESULT,
                GameResult(score = model.getPoints(), date = Date())
            )
        }
        startActivity(finish)
        finish()
    }

    override fun onPause() {
        super.onPause()
        pauseGame()
    }

    override fun onResume() {
        super.onResume()
        if (!model.isGameStarted()) {
            startGame()
            model.setGameStarted()
        } else {
            model.pauseRotation.value = false
        }
    }

    private fun startGame() {
        model.startMusic()
        moveBlockDown = lifecycleScope.launch(start = CoroutineStart.ATOMIC) { model.runGame() }
        model.pauseRotation.value = true
    }

    private fun pauseGame() {
        if (!model.isGamePaused()) {
            moveBlockDown.cancel()
            model.pauseMusic()
            model.setGamePaused()
            model.pauseRotation.value = false
        }
    }

    private fun pauseButtonClicked() {
        if (model.isGamePaused()) {
            resumeAction.execute()
            resumeGame()
        } else
            pauseGame()
    }

    private fun resumeGame() {
        if (model.isGamePaused()) {
            startGame()
            model.setGameResume()
        }
    }

}