package com.pablo.tetris.presentation.history.queries

import android.app.Activity
import android.text.InputType
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.pablo.tetris.infra.database.Player
import com.pablo.tetris.presentation.history.model.HistoryViewModel
import kotlinx.coroutines.runBlocking


class SearchPlayerByName(val viewModel: HistoryViewModel, private val activity: Activity) : Query {

    override fun get(): List<Player> {
        return getResult(activity)
    }

    private fun getResult(activity: Activity): List<Player> {
        val result = mutableListOf<Player>()
        val editText = EditText(activity)
        editText.inputType = InputType.TYPE_CLASS_TEXT
        AlertDialog.Builder(activity)
            .setTitle("Title")
            .setMessage("Message")
            .setView(editText)
            .setPositiveButton("OK") { _, _ ->
                val input = editText.text.toString()
                run {
                    runBlocking {
                        if (input.isNotBlank())
                            result.addAll(viewModel.query { repo -> repo.getPlayersThatMatch(input) })
                        else
                            Toast.makeText(activity, "Please enter a name", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .setNegativeButton("Cancel") { _, _ ->
                Toast.makeText(activity, "Cancelled", Toast.LENGTH_SHORT).show()
            }
            .create()
            .show()

        return result
    }

}
