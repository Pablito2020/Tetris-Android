package com.pablo.tetris.presentation.history.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pablo.tetris.R
import com.pablo.tetris.presentation.history.model.HistoryViewModel
import com.pablo.tetris.presentation.history.queries.PlayersOrderedByPointsQuery
import com.pablo.tetris.presentation.history.queries.SearchPlayerByName
import com.pablo.tetris.presentation.history.view.PlayerAdapter
import com.pablo.tetris.presentation.history.view.Spinner

class HistorialFragment : Fragment() {

    private val historyViewModel: HistoryViewModel by lazy {
        HistoryViewModel(requireActivity().application)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpRecyclerView()
        setUpSpinner()
        setUpAutoComplete()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpRecyclerView() {
        historyViewModel.executeQuery()
        val adapter = PlayerAdapter(historyViewModel, historyViewModel.getPlayers())
        val recyclerViewHistory = requireView().findViewById<RecyclerView>(R.id.recyclerViewHistory)
        recyclerViewHistory.adapter = adapter
        val manager = LinearLayoutManager(context)
        recyclerViewHistory.layoutManager = manager
        recyclerViewHistory.addItemDecoration(DividerItemDecoration(recyclerViewHistory.context, manager.orientation))
        historyViewModel.updatedDataBase.observe(this) { adapter.players = historyViewModel.getPlayers(); adapter.notifyDataSetChanged() }
    }

    private fun setUpSpinner() {
        val spinner = requireView().findViewById<android.widget.Spinner>(R.id.chooseActionSpinner)
        spinner.onItemSelectedListener = Spinner(historyViewModel)
    }

    private fun setUpAutoComplete() {
        val autoComplete = requireView().findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        val list: List<String> = historyViewModel.getPlayers().map {p -> p.name}.distinct()
        autoComplete.setAdapter(ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, list))
        autoComplete.threshold = 0
        autoComplete.addTextChangedListener {
            val query = if (it.toString().isEmpty()) PlayersOrderedByPointsQuery(historyViewModel) else SearchPlayerByName(historyViewModel, it.toString())
            historyViewModel.executeQuery(query)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_historial, container, false)
    }

}