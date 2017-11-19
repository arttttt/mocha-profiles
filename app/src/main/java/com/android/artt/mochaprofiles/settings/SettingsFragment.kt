package com.android.artt.mochaprofiles.settings

import android.content.Context
import android.os.Bundle
import android.preference.CheckBoxPreference
import android.preference.Preference
import android.preference.PreferenceFragment
import com.android.artt.mochaprofiles.R
import com.android.artt.mochaprofiles.profiles.Constants
import com.android.artt.mochaprofiles.profiles.ProfilesManager

class SettingsFragment: PreferenceFragment(), Preference.OnPreferenceChangeListener {

    private val mProfilesCheckBox by lazy {
        findPreference(getString(R.string.alternative_profiles_key)) as CheckBoxPreference
    }

    private val mSharedPreferences by lazy { context.getSharedPreferences( Constants.PREFERENCES, Context.MODE_PRIVATE) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        addPreferencesFromResource(R.xml.main_preference)
        mProfilesCheckBox.onPreferenceChangeListener = this
    }

    override fun onPreferenceChange(preference: Preference?, newValue: Any?): Boolean =
            when (preference) {
                mProfilesCheckBox -> {
                    mSharedPreferences.edit().putBoolean(Constants.PROFILES_ALTERNATIVE_KEY, newValue as Boolean).apply()
                    ProfilesManager.instance.applyProfile(getSavedProfile(), newValue)
                    true
                }
                else -> false
            }

    private fun getSavedProfile(): String = mSharedPreferences.getString(Constants.PROFILE_KEY, ProfilesManager.DEFAULT_PROFILE)
}