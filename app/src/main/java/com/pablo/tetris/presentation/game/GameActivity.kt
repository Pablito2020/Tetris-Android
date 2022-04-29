package com.pablo.tetris.presentation.game

import GameFacade
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.pablo.tetris.databinding.ActivityGameBinding
import com.pablo.tetris.presentation.common.HideStatusBarActivity
import com.pablo.tetris.presentation.finished.FinishedActivity
import com.pablo.tetris.presentation.game.grid.GameAdapter
import com.pablo.tetris.presentation.game.grid.orientation.ItemFactory
import com.pablo.tetris.presentation.game.grid.colors.VibrantColorChooser
import com.pablo.tetris.presentation.game.grid.style.Style
import com.pablo.tetris.presentation.game.grid.style.StyleCreator
import com.pablo.tetris.presentation.game.grid.style.StyleFactory
import com.pablo.tetris.presentation.getImageButtons
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.reflect.typeOf


class GameActivity : HideStatusBarActivity(), View.OnClickListener {

    private lateinit var gameViewModel: GameViewModel
    private lateinit var binding: ActivityGameBinding
    private lateinit var adapter: GameAdapter
    private lateinit var style: StyleCreator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        style = StyleFactory.getStyleCreator(Style.SATURATED)
        setUpUpdateObserver()
        setUpGridView()
        setUpButtons()
        setUpDownCoroutine()
    }

    private fun setUpGridView() {
        val item = ItemFactory.getItem(resources.configuration.orientation)
        adapter = GameAdapter(getFlatGrid(), this, style.getColorCellChooser(), item)
        binding.GameGrid.adapter = adapter
    }

    private fun setUpButtons() {
        binding.root.getImageButtons().forEach { it.setOnClickListener(this) }
        binding.DownButton.setOnLongClickListener { gameViewModel.dropBlock();true }
    }

    private fun updateGrid() {
        adapter.gameCells = getFlatGrid()
        adapter.notifyDataSetChanged()
        binding.PointsText.text = gameViewModel.gameFacade.value!!.getScore().value.toString()
        val typeOfBlock = gameViewModel.gameFacade.value!!.getNextBlock()
        binding.NextBlockImage.setImageResource(style.getBlockCreator().getImageId(typeOfBlock))
    }

    private fun setUpDownCoroutine() {
        lifecycleScope.launch {
            while (!gameViewModel.gameFacade.value!!.hasFinished()) {
                gameViewModel.down()
                delay(1000)
            }
        }
    }

    private fun setUpUpdateObserver() {
        gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)
        gameViewModel.setUp(GameFacade(ghost = true))
        gameViewModel.gameFacade.observe(this) {
            if (!it.hasFinished())
                updateGrid()
            else
                finishGame()
        }
    }

    private fun finishGame() {
        val finish = Intent(this, FinishedActivity::class.java)
        startActivity(finish)
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