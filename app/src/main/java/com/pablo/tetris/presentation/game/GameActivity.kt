package com.pablo.tetris.presentation.game

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.pablo.tetris.databinding.ActivityGameBinding
import com.pablo.tetris.presentation.common.GAME_RESULT
import com.pablo.tetris.presentation.common.HideStatusBarActivity
import com.pablo.tetris.presentation.common.getButtons
import com.pablo.tetris.presentation.finished.FinishedActivity
import com.pablo.tetris.presentation.game.actions.Action
import com.pablo.tetris.presentation.game.actions.ResumeToastAction
import com.pablo.tetris.presentation.game.grid.GameAdapter
import com.pablo.tetris.presentation.game.results.DateGetter
import com.pablo.tetris.presentation.game.results.GameResult
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class GameActivity : HideStatusBarActivity(), View.OnClickListener {

    private lateinit var model: GameViewModel
    private lateinit var binding: ActivityGameBinding
    private lateinit var adapter: GameAdapter
    private lateinit var resumeAction: Action
    private val factory = SettingsFactory()
    private lateinit var moveBlockDown: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        factory.fromIntent(intent)
        setUpViewModel()
        setUpGridView()
        setUpButtons()
        setUpResumeAction()
    }

    private fun setUpViewModel() {
        model = ViewModelProvider(this).get(GameViewModel::class.java)
        model.setUp(factory.getFacade(), factory.getSpeedStrategy())
        model.setUpMusic(factory.hasMusic(), this)
        model.gameFacade.observe(this) {
            if (!it.hasFinished())
                updateScreen()
            else
                finishGame()
        }
    }

    private fun setUpGridView() {
        val cellColors =
            factory.getStyle(this).getColorCellChooser()
        adapter = GameAdapter(model.getGrid(), cellColors)
        binding.GameGrid.adapter = adapter
    }

    private fun setUpButtons() {
        binding.root.getButtons().forEach { it.setOnClickListener(this) }
        binding.pauseButton.setOnClickListener(this)
        binding.DownButton.setOnLongClickListener { model.dropBlock();true }
    }

    private fun updateScreen() {
        adapter.gameCells = model.getGrid()
        adapter.notifyDataSetChanged()
        binding.PointsText.text = model.getPoints()
        val typeOfBlock = model.getNextBlock()
        binding.NextBlockImage.setImageResource(
            factory.getStyle(this).getBlockCreator().getImageId(typeOfBlock)
        )
    }

    private fun setUpResumeAction() {
        resumeAction = ResumeToastAction(this)
    }

    private fun finishGame() {
        val finish = Intent(this@GameActivity, FinishedActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
            putExtra(
                GAME_RESULT,
                GameResult(score = model.getPoints(), date = DateGetter.getDate())
            )
        }
        startActivity(finish)
        finish()
    }

    override fun onClick(p0: View) = when (p0.id) {
        binding.DownButton.id -> model.down()
        binding.LeftButton.id -> model.left()
        binding.RightButton.id -> model.right()
        binding.RotateLeft.id -> model.rotateLeft()
        binding.RotateRight.id -> model.rotateRight()
        binding.pauseButton.id -> pauseButtonClicked()
        else -> throw UnsupportedOperationException("Unknown button")
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
            binding.pauseButton.setState(PlayPauseView.STATE_PAUSE)
            binding.pauseButton.fadeIn()
        }
    }

    private fun startGame() {
        model.startMusic()
        moveBlockDown = lifecycleScope.launch { model.runGame() }
        binding.pauseButton.setState(PlayPauseView.STATE_PLAY)
        binding.pauseButton.fadeIn()
    }

    private fun pauseGame() {
        if (!model.isGamePaused()) {
            moveBlockDown.cancel()
            model.pauseMusic()
            model.setGamePaused()
            binding.pauseButton.setState(PlayPauseView.STATE_PAUSE)
            binding.pauseButton.fadeIn()
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