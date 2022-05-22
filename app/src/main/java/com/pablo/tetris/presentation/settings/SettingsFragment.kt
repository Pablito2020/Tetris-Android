package com.pablo.tetris.presentation.settings

import android.os.Bundle
import android.widget.Toast
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.pablo.tetris.R
import com.pablo.tetris.domain.user.ValidateName

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        val editTextName: EditTextPreference = preferenceScreen.findPreference<Preference>("name") as EditTextPreference
        editTextName.setOnPreferenceChangeListener() { _, newValue ->
            val name = newValue as String
            val result = ValidateName(name).execute()
            if (!result.success)
                Toast.makeText(requireContext(), result.errorMessage!!.asString(requireContext()), Toast.LENGTH_SHORT).show()
            result.success
        }
    }
}