package com.pablo.tetris.presentation.game

import GameFacade
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.pablo.tetris.databinding.ActivityGameBinding
import com.pablo.tetris.presentation.common.HideStatusBarActivity
import com.pablo.tetris.presentation.game.colors.VibrantColorChooser
import com.pablo.tetris.presentation.getImageButtons
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class GameActivity : HideStatusBarActivity(), View.OnClickListener {

    private lateinit var gameViewModel: GameViewModel
    private lateinit var binding: ActivityGameBinding
    private lateinit var adapter: GameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpGame()
        setUpGridView()
        setUpButtons()
        setUpDownCoroutine()
        setUpUpdateObserver()
    }

    private fun setUpGame() {
        gameViewModel = GameViewModel(GameFacade(ghost = true))
        gameViewModel.start()
    }

    private fun setUpGridView() {
        val colorChooser = VibrantColorChooser()
        adapter = GameAdapter(getFlatGrid(), this, colorChooser)
        binding.GameGrid.adapter = adapter
    }

    private fun setUpButtons() {
        binding.root.getImageButtons().forEach { it.setOnClickListener(this) }
        binding.DownButton.setOnLongClickListener { gameViewModel.dropBlock();true }
    }

    private fun updateGrid() {
        adapter.gameCells = getFlatGrid()
        adapter.notifyDataSetChanged()
    }

    private fun setUpDownCoroutine() {
        MainScope().launch {
            while (!gameViewModel.gameFacade.value!!.hasFinished()) {
                delay(1000)
                gameViewModel.down()
            }
        }
    }

    private fun setUpUpdateObserver() {
        gameViewModel.gameFacade.observe(this) {
            if (!it.hasFinished())
                updateGrid()
            else
                finishGame()
        }
    }

    private fun finishGame() {
        Toast.makeText(this, "Game Over", Toast.LENGTH_LONG).show()
        finish()
    }

    private fun getFlatGrid() = gameViewModel.gameFacade.value!!.getGrid().flatMap { it.toList() }

    override fun onClick(p0: View) = when (p0.id) {
        binding.DownButton.id -> gameViewModel.down()
        binding.LeftButton.id -> gameViewModel.left()
        binding.RightButton.id -> gameViewModel.right()
        binding.RotateLeft.id -> gameViewModel.rotateLeft()
        binding.RotateRight.id -> gameViewModel.rotateRight()
        else -> throw UnsupportedOperationException("Unknown button")
    }

}