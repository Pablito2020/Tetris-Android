package com.pablo.tetris.presentation.game

import GameFacade
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.pablo.tetris.databinding.ActivityGameBinding
import com.pablo.tetris.presentation.game.colors.VibrantColorChooser


class GameActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityGameBinding
    private lateinit var gameFacade: GameFacade
    private lateinit var adapter: GameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        gameFacade = GameFacade(ghost = true)
        gameFacade.start()
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter =
            GameAdapter(gameFacade.getGrid().flatMap { it.toList() }, this, VibrantColorChooser())
        binding.GameGrid.adapter = adapter
        setUpButtons()
    }

    private fun setUpButtons() {
        binding.DownButton.setOnClickListener(this)
        binding.LeftButton.setOnClickListener(this)
        binding.RightButton.setOnClickListener(this)
        binding.RotateLeft.setOnClickListener(this)
        binding.RotateRight.setOnClickListener(this)
    }


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