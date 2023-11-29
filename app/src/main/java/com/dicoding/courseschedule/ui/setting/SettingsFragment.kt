package com.dicoding.courseschedule.ui.setting

import android.os.*
import android.widget.*
import androidx.appcompat.app.*
import androidx.preference.*
import com.dicoding.courseschedule.R
import com.dicoding.courseschedule.notification.*
import com.dicoding.courseschedule.util.*

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        //TODO 10 : Update theme based on value in ListPreference
        val switchTheme: ListPreference = findPreference(getString(R.string.pref_key_dark))!!
        switchTheme.setOnPreferenceChangeListener { _, newValue ->
            val stringValue = newValue.toString()
            if (stringValue == getString(R.string.pref_dark_auto)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    updateTheme(NightMode.AUTO.value)
                } else {
                    updateTheme(NightMode.ON.value)
                }
            } else if (stringValue == getString(R.string.pref_dark_off)) {
                updateTheme(NightMode.OFF.value)
            } else {
                updateTheme(NightMode.ON.value)
            }
            true
        }

        //TODO 11 : Schedule and cancel notification in DailyReminder based on SwitchPreference
        val switchNotification: SwitchPreference =
            findPreference(getString(R.string.pref_key_notify))!!
        switchNotification.setOnPreferenceChangeListener { _, newValue ->
            val broadcastReceiver = DailyReminder()
            if (newValue == true) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    broadcastReceiver.setDailyReminder(requireContext())
                }
                Toast.makeText(activity, "Enable", Toast.LENGTH_SHORT).show()
            } else {
                broadcastReceiver.cancelAlarm(requireContext())
                Toast.makeText(activity, "Disable", Toast.LENGTH_SHORT).show()
            }
            true
        }
    }

    private fun updateTheme(nightMode: Int): Boolean {
        AppCompatDelegate.setDefaultNightMode(nightMode)
        requireActivity().recreate()
        return true
    }
}

