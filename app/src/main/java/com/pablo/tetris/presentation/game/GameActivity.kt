package com.pablo.tetris.presentation.game

import GameFacade
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.pablo.tetris.databinding.ActivityGameBinding
import com.pablo.tetris.presentation.game.colors.VibrantColorChooser
import com.pablo.tetris.presentation.getButtons


class GameActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityGameBinding
    private lateinit var gameFacade: GameFacade
    private lateinit var adapter: GameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUpGame()
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpGridView()
        binding.root.getButtons().forEach { it.setOnClickListener(this) }
    }

    private fun setUpGame() {
        gameFacade = GameFacade(ghost = true)
        gameFacade.start()
    }

    private fun setUpGridView() {
        val colorChooser = VibrantColorChooser()
        adapter = GameAdapter(getFlatGrid(),this, colorChooser)
        binding.GameGrid.adapter = adapter
    }

    private fun getFlatGrid() = gameFacade.getGrid().flatMap { it.toList() }

    override fun onClick(p0: View) {
        when (p0.id) {
            binding.DownButton.id -> gameFacade.down()
            binding.LeftButton.id -> gameFacade.left()
            binding.RightButton.id -> gameFacade.right()
            binding.RotateLeft.id -> gameFacade.rotateLeft()
            binding.RotateRight.id -> gameFacade.rotateRight()
        }
        adapter.gameCells = gameFacade.getGrid().flatMap { it.toList() }
        adapter.notifyDataSetChanged()
    }

}