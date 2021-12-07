package com.orlove101.android.mvvmnewsapp.ui.fragments

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.*
import com.orlove101.android.mvvmnewsapp.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : PreferenceFragmentCompat() {
    private var switchThemePreference: SwitchPreferenceCompat? = null

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        switchThemePreference = findPreference<SwitchPreferenceCompat>("theme")

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            switchThemePreference?.title = getString(R.string.dark_theme_title)
            switchThemePreference?.isChecked = true
        } else {
            switchThemePreference?.title = getString(R.string.theme_title)
            switchThemePreference?.isChecked = false
        }

        switchThemePreference?.setOnPreferenceChangeListener { preference, newValue ->
            if (newValue == true) {
                preference.title = getString(R.string.dark_theme_title)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                preference.title = getString(R.string.theme_title)
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            true
        }
    }
}