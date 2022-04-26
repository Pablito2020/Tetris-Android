package com.pablo.tetris.presentation

import GameFacade
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import board.Cell
import com.pablo.tetris.R
import com.pablo.tetris.databinding.ActivityGameBinding
import game.GameCell


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
        adapter = GameAdapter(gameFacade.getGrid().flatMap { it.toList() }, this)
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

    class GameAdapter(var gameCells: List<GameCell>, private val context: Context) : BaseAdapter() {

        override fun getCount(): Int = 10 * 20

        override fun getItem(p0: Int): Any = gameCells[p0]

        override fun getItemId(p0: Int): Long = p0.toLong()

        override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
            val view = p1 ?: View.inflate(context, R.layout.grid_item, null)
            val cell: TextView = view.findViewById(R.id.grid_item)
            cell.background =
                AppCompatResources.getDrawable(context, getColorForCell(gameCells[p0]))!!
            return view
        }

        private fun getColorForCell(gameCell: GameCell): Int {
            if (gameCell.isGhostBlockCell) {
                return R.color.vibrant_gray
            }
            return when (gameCell.cell) {
                Cell.EMPTY -> R.color.black
                Cell.I_BLOCK -> R.color.vibrant_yellow
                Cell.J_BLOCK -> R.color.vibrant_blue
                Cell.L_BLOCK -> R.color.vibrant_cyan
                Cell.SQUARE_BLOCK -> R.color.vibrant_green
                Cell.S_BLOCK -> R.color.vibrant_red
                Cell.T_BLOCK -> R.color.vibrant_magenta
                Cell.Z_BLOCK -> R.color.vibrant_white
            }
        }

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