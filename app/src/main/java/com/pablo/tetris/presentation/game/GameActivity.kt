package com.pablo.tetris.presentation.game

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.pablo.tetris.R
import com.pablo.tetris.databinding.ActivityGameBinding
import com.pablo.tetris.presentation.common.HideStatusBarActivity
import com.pablo.tetris.presentation.finished.FinishedActivity
import com.pablo.tetris.presentation.game.grid.GameAdapter
import com.pablo.tetris.presentation.game.toast.oneSecondToast
import com.pablo.tetris.presentation.getImageButtons
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class GameActivity : HideStatusBarActivity(), View.OnClickListener {

    private lateinit var gameViewModel: GameViewModel
    private lateinit var binding: ActivityGameBinding
    private lateinit var adapter: GameAdapter
    private val factory = SettingsFactory
    private lateinit var moveBlockDown: Job

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        factory.fromIntent(intent)
        setUpViewModel()
        setUpGridView()
        setUpButtons()
    }

    private fun setUpViewModel() {
        gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        gameViewModel.setUp(factory.getFacade())
        gameViewModel.setUpMusic(factory.hasMusic(), this)
        gameViewModel.gameFacade.observe(this) {
            if (!it.hasFinished())
                updateScreen()
            else
                finishGame()
        }
    }

    private fun setUpGridView() {
        val cellColors =
            factory.getStyle(this).getColorCellChooser()
        adapter = GameAdapter(gameViewModel.getGrid(), cellColors)
        binding.GameGrid.adapter = adapter
    }

    private fun setUpButtons() {
        binding.root.getImageButtons().forEach { it.setOnClickListener(this) }
        binding.DownButton.setOnLongClickListener { gameViewModel.dropBlock();true }
        binding.pauseButton.setImageResource(getPauseButtonResource())
    }

    private fun updateScreen() {
        adapter.gameCells = gameViewModel.getGrid()
        adapter.notifyDataSetChanged()
        binding.PointsText.text = gameViewModel.getPoints()
        val typeOfBlock = gameViewModel.getNextBlock()
        binding.NextBlockImage.setImageResource(
            factory.getStyle(this).getBlockCreator().getImageId(typeOfBlock)
        )
    }

    private fun finishGame() {
        val finish = Intent(this, FinishedActivity::class.java)
        startActivity(finish)
        finish()
    }

    override fun onClick(p0: View) = when (p0.id) {
        binding.DownButton.id -> gameViewModel.down()
        binding.LeftButton.id -> gameViewModel.left()
        binding.RightButton.id -> gameViewModel.right()
        binding.RotateLeft.id -> gameViewModel.rotateLeft()
        binding.RotateRight.id -> gameViewModel.rotateRight()
        binding.pauseButton.id -> pauseButtonClicked()
        else -> throw UnsupportedOperationException("Unknown button")
    }

    override fun onPause() {
        super.onPause()
        pauseGame()
    }

    override fun onResume() {
        super.onResume()
        resumeGame()
    }

    private fun resumeGame() {
        if (!gameViewModel.gamePaused.value!!) {
            gameViewModel.startMusic()
            moveBlockDown = lifecycleScope.launch { gameViewModel.run() }
        }
    }

    private fun pauseGame() {
        if (!gameViewModel.gamePaused.value!!) {
            moveBlockDown.cancel()
            gameViewModel.pauseMusic()
        }
    }

    private fun pauseButtonClicked() {
        if (gameViewModel.gamePaused.value!!) {
            gameViewModel.gamePaused.value = false
            waitAndInformAboutResume()
            resumeGame()
        } else {
            pauseGame()
            gameViewModel.gamePaused.value = true
        }
        binding.pauseButton.setImageResource(getPauseButtonResource())
    }

    private fun getPauseButtonResource() =
        if (gameViewModel.gamePaused.value!!) android.R.drawable.ic_media_play else android.R.drawable.ic_media_pause

    private fun waitAndInformAboutResume() {
        val millisecondsDelay = 1500L
        val waitingSeconds = 3
        (waitingSeconds downTo 1).forEach { displayToast(it); Thread.sleep(millisecondsDelay) }
    }

    private fun displayToast(iteration: Int) {
        oneSecondToast(this, "${getString(R.string.resumingin)} $iteration")
    }

}