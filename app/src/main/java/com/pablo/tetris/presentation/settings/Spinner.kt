package com.pablo.tetris.presentation.settings

import android.R.layout.simple_spinner_dropdown_item
import android.content.Context
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.PopupWindow
import com.pablo.tetris.presentation.game.grid.style.Style

class Spinner(private val viewModel: SettingsModel) : AdapterView.OnItemSelectedListener {

    object Adapter {
        fun get(context: Context) = ArrayAdapter(
            context,
            simple_spinner_dropdown_item,
            Style.values().map { it.name.lowercase() })
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        viewModel.update(DataValue.Theme(p2))
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {}

}

/*
Method for avoiding the drop down focus on the spinner (doesn't show the navbar buttons)
 */
fun android.widget.Spinner.avoidDropdownFocus() {
    try {
        val isAppCompat = this is androidx.appcompat.widget.AppCompatSpinner
        val spinnerClass =
            if (isAppCompat) androidx.appcompat.widget.AppCompatSpinner::class.java else Spinner::class.java
        val popupWindowClass =
            if (isAppCompat) androidx.appcompat.widget.ListPopupWindow::class.java else android.widget.ListPopupWindow::class.java

        val listPopup = spinnerClass
            .getDeclaredField("mPopup")
            .apply { isAccessible = true }
            .get(this)
        if (popupWindowClass.isInstance(listPopup)) {
            val popup = popupWindowClass
                .getDeclaredField("mPopup")
                .apply { isAccessible = true }
                .get(listPopup)
            if (popup is PopupWindow) {
                popup.isFocusable = false
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}
