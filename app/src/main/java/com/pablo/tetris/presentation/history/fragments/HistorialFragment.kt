package com.pablo.tetris.presentation.history.fragments

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pablo.tetris.R
import com.pablo.tetris.infra.database.Player
import com.pablo.tetris.infra.database.PlayerApplication
import com.pablo.tetris.presentation.history.CurrentGameLogActivity
import com.pablo.tetris.presentation.history.model.HistoryViewModel
import com.pablo.tetris.presentation.history.view.PlayerAdapter
import com.pablo.tetris.presentation.history.view.Spinner
import kotlin.properties.Delegates

@SuppressLint("NotifyDataSetChanged")
class HistorialFragment : Fragment() {

    private lateinit var historyViewModel: HistoryViewModel
    private var hasLogFragment by Delegates.notNull<Boolean>()
    private lateinit var adapter: PlayerAdapter

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
        setUpAdapterRecyclerView()
        setUpRecyclerViewUi()
        setUpRecyclerViewObserver()
    }

    private fun setUpAdapterRecyclerView() {
        adapter = PlayerAdapter(
            historyViewModel.getPlayers(),
            deleteButtonListener = { deletePlayer(it) },
            itemClickListener = { showLogForPlayer(it) },
            itemLongCLickListener = { view, player -> showMenuForFiltering(view, player) }
        )
    }

    private fun setUpRecyclerViewUi() {
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
    }

    private fun setUpRecyclerViewObserver() {
        historyViewModel.updatedDataBase.observe(viewLifecycleOwner) {
            val autoComplete = requireView().findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
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

    private fun deletePlayer(player: Player) {
        androidx.appcompat.app.AlertDialog.Builder(requireContext())
            .setTitle(R.string.warning)
            .setMessage(requireContext().getString(R.string.delete_game_warning, player.name))
            .setNegativeButton(R.string.cancel) { _, _ -> Toast.makeText(context, R.string.deletion_canceled, Toast.LENGTH_SHORT).show() }
            .setPositiveButton(R.string.ok) { _, _ -> historyViewModel.deletePlayer(player); Toast.makeText(context, R.string.deleted_player_game, Toast.LENGTH_SHORT).show() }
            .create()
            .show()
    }

    private fun showLogForPlayer(player: Player) {
        historyViewModel.showLogForPlayer(player)
        if (!hasLogFragment) {
            val intent = Intent(requireContext().applicationContext, CurrentGameLogActivity::class.java)
            requireContext().startActivity(intent)
        }
    }

    private fun showMenuForFiltering(root: View, player: Player) {
        val popupMenu = PopupMenu(requireContext(), root)
        popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)
        popupMenu.setOnMenuItemClickListener {
            val autoComplete = requireView().findViewById<AutoCompleteTextView>(R.id.autoCompleteTextView)
            autoComplete.setText(player.name)
            autoComplete.clearFocus()
            true
        }
        popupMenu.show()
    }

}