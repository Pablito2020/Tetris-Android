package com.pablo.tetris

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.pablo.tetris.presentation.common.getButtons
import com.pablo.tetris.presentation.game.GameViewModel
import com.pablo.tetris.presentation.game.PlayPauseView
import com.pablo.tetris.presentation.game.SettingsFactory
import com.pablo.tetris.presentation.game.State
import com.pablo.tetris.presentation.game.grid.GameAdapter

class GameFragment(private val factory: SettingsFactory) : Fragment(), View.OnClickListener {

    private lateinit var adapter: GameAdapter
    private lateinit var viewModel: GameViewModel
    private lateinit var my_view: View

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpObservers()
    }

    override fun onStart() {
        super.onStart()
        setUpObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        my_view = inflater.inflate(R.layout.fragment_game, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(GameViewModel::class.java)
        setUpGridView()
        setUpButtons()
        return my_view
    }

    private fun setUpGridView() {
        val cellColors = factory.getStyle(requireContext()).getColorCellChooser()
        adapter = GameAdapter(viewModel.getGrid(), cellColors)
        val gameGrid: GridView = my_view.findViewById(R.id.GameGrid)
        gameGrid.adapter = adapter
    }

    private fun setUpButtons() {
        (my_view.rootView as ViewGroup).getButtons().forEach { it.setOnClickListener(this) }
        val pauseButton: PlayPauseView = my_view.findViewById(R.id.pauseButton)
        pauseButton.setOnClickListener(this)
        val downButton: Button = my_view.findViewById(R.id.DownButton)
        downButton.setOnLongClickListener { viewModel.dropBlock();true }
    }

    private fun setUpObservers() {
        viewModel.pauseRotation.observe(viewLifecycleOwner) {
            if (it)
                showPlayButtonStatus()
            else
                showPauseButtonStatus()
        }
        viewModel.updateScreen.observe(viewLifecycleOwner) {
            updateScreen()
        }
    }

    private fun updateScreen() {
        adapter.gameCells = viewModel.getGrid()
        adapter.notifyDataSetChanged()
        val pointsText: TextView = my_view.findViewById(R.id.PointsText)
        pointsText.text = viewModel.getPoints()
        val typeOfBlock = viewModel.getNextBlock()
        val imageNextBlock: ImageView = my_view.findViewById(R.id.NextBlockImage)
        imageNextBlock.setImageResource(
            factory.getStyle(requireContext()).getBlockCreator().getImageId(typeOfBlock)
        )
    }

    override fun onClick(p0: View) = when (p0.id) {
        R.id.DownButton -> viewModel.down()
        R.id.LeftButton -> viewModel.left()
        R.id.RightButton -> viewModel.right()
        R.id.RotateLeft -> viewModel.rotateLeft()
        R.id.RotateRight -> viewModel.rotateRight()
        R.id.pauseButton -> viewModel.pauseButtonClicked.value = true
        else -> throw UnsupportedOperationException("Unknown button")
    }

    private fun showPauseButtonStatus() {
        val pauseButton: PlayPauseView = my_view.findViewById(R.id.pauseButton)
        pauseButton.setState(State.PAUSE)
        pauseButton.fadeIn()
    }

    private fun showPlayButtonStatus() {
        val pauseButton: PlayPauseView = my_view.findViewById(R.id.pauseButton)
        pauseButton.setState(State.PLAY)
        pauseButton.fadeIn()
    }


}