package com.pablo.tetris.presentation.history.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.pablo.tetris.R
import com.pablo.tetris.infra.database.PlayerApplication
import com.pablo.tetris.presentation.history.model.HistoryViewModel

class LogHistoryFragment : Fragment() {

    private lateinit var historyViewModel: HistoryViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        historyViewModel = ViewModelProvider(
            activity?.application as PlayerApplication,
            HistoryViewModel.HistoryViewModelFactory((activity?.application as PlayerApplication).repository)
        ).get(HistoryViewModel::class.java)
        historyViewModel.setDefaultLogValue(hasItemsText=requireContext().getString(R.string.please_click_item), noItemsText=requireContext().getString(R.string.no_items_in_database))
        requireView().findViewById<TextView>(R.id.logTextFragment).text =
            historyViewModel.currentLog.value
        historyViewModel.currentLog.observe(viewLifecycleOwner) {
            it?.let {
                requireView().findViewById<TextView>(R.id.logTextFragment).text = it
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_log, container, false)
    }

}