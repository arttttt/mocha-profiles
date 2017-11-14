package com.android.artt.mochaprofiles.settings

import android.content.Context
import android.os.Bundle
import android.preference.CheckBoxPreference
import android.preference.Preference
import android.preference.PreferenceFragment
import com.android.artt.mochaprofiles.R

class SettingsFragment: PreferenceFragment(), Preference.OnPreferenceChangeListener {

    private val mProfilesCheckBox by lazy {
        findPreference(getString(R.string.alternative_profiles_key)) as CheckBoxPreference
    }

    private val mSharedPrefereces by lazy { context.getSharedPreferences( PREFERENCES, Context.MODE_PRIVATE) }

    val PREFERENCES
        get() = "profiles_preferences"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addPreferencesFromResource(R.xml.main_preference)
        mProfilesCheckBox.onPreferenceChangeListener = this
    }

    override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean =
            when (preference) {
                mProfilesCheckBox -> {
                    mSharedPrefereces.edit().putBoolean(getString(R.string.alternative_profiles_key), false).apply()
                    true
                }
                else -> true
            }
}