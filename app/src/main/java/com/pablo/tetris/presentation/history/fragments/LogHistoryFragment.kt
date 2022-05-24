package com.pablo.tetris.presentation.history.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.pablo.tetris.R
import com.pablo.tetris.infra.database.PlayerApplication
import com.pablo.tetris.presentation.history.GameHistorialActivity
import com.pablo.tetris.presentation.history.model.HistoryViewModel

class LogHistoryFragment : Fragment() {

    private val historyViewModel: HistoryViewModel by viewModels<HistoryViewModel>({activity as GameHistorialActivity}) {
        HistoryViewModel.HistoryViewModelFactory((activity?.application as PlayerApplication).repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        historyViewModel.currentLog.observe(viewLifecycleOwner) {
            it?.let {
                requireView().findViewById<TextView>(R.id.logHistoryTextView).text = it
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_log_history, container, false)
    }

}