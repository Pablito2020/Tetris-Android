package com.pablo.tetris.presentation.history.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pablo.tetris.R
import com.pablo.tetris.infra.database.PlayerApplication
import com.pablo.tetris.presentation.history.model.HistoryViewModel
import com.pablo.tetris.presentation.history.view.PlayerAdapter
import com.pablo.tetris.presentation.history.view.Spinner
import kotlin.properties.Delegates

@SuppressLint("NotifyDataSetChanged")
class HistorialFragment : Fragment() {

    private lateinit var historyViewModel: HistoryViewModel

    private var hasLogFragment by Delegates.notNull<Boolean>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        historyViewModel = ViewModelProvider(
            activity?.application as PlayerApplication,
            HistoryViewModel.HistoryViewModelFactory((activity?.application as PlayerApplication).repository)
        ).get(HistoryViewModel::class.java)
        setUpRecyclerView()
        setUpSpinner()
        setUpAutoComplete()
    }

    private fun setUpRecyclerView() {
        historyViewModel.executeQuery()
        val adapter = PlayerAdapter(
            historyViewModel,
            historyViewModel.getPlayers(),
            hasLogFragment,
            requireContext()
        )
        val recyclerViewHistory = requireView().findViewById<RecyclerView>(R.id.recyclerViewHistory)
        recyclerViewHistory.adapter = adapter
        val manager = LinearLayoutManager(context)
        recyclerViewHistory.layoutManager = manager
        recyclerViewHistory.addItemDecoration(
            DividerItemDecoration(
                recyclerViewHistory.context,
                manager.orientation
            )
        )
        historyViewModel.updatedDataBase.observe(viewLifecycleOwner) {
            val autoComplete =
                requireView().findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
            val names = autoComplete.text.toString()
            adapter.players = historyViewModel.getAutoCompleteResult(names)
            adapter.notifyDataSetChanged()
        }
    }

    private fun setUpSpinner() {
        val spinner = requireView().findViewById<android.widget.Spinner>(R.id.chooseActionSpinner)
        spinner.onItemSelectedListener = Spinner(historyViewModel)
    }

    private fun setUpAutoComplete() {
        val autoComplete =
            requireView().findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
        val list: List<String> =
            historyViewModel.getAutoCompleteResult(autoComplete.text.toString()).map { p -> p.name }
                .distinct()
        autoComplete.setAdapter(
            ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                list
            )
        )
        autoComplete.threshold = 0
        autoComplete.addTextChangedListener {
            val adapter =
                requireView().findViewById<RecyclerView>(R.id.recyclerViewHistory).adapter as PlayerAdapter
            adapter.players = historyViewModel.getAutoCompleteResult(it.toString())
            adapter.notifyDataSetChanged()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        hasLogFragment = arguments?.getSerializable(HAS_LOG_FRAGMENT) as Boolean
        return inflater.inflate(R.layout.fragment_historial_search, container, false)
    }

    companion object BundleConstants {
        const val HAS_LOG_FRAGMENT = "hasLogFragment"
    }

}