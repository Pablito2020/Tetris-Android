package com.pablo.tetris.presentation.game.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.pablo.tetris.R
import com.pablo.tetris.infra.logs.LoggerGetter
import com.pablo.tetris.presentation.game.GameViewModel

class LogFragment : Fragment() {

    private lateinit var text: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel = ViewModelProvider(requireActivity()).get(GameViewModel::class.java)
        text = requireView().findViewById(R.id.logTextFragment)
        viewModel.updatedLog.observe(viewLifecycleOwner) {
            text.text = LoggerGetter.get().getLog().joinToString(separator = "\n") { it.asString(requireContext()) }
        }
        text.text = LoggerGetter.get().getLog().joinToString(separator = "\n") { it.asString(requireContext()) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_log, container, false)
    }

}