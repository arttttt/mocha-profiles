package com.android.artt.mochaprofiles.settings

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.preference.CheckBoxPreference
import android.preference.Preference
import android.preference.PreferenceFragment
import android.util.Log
import com.android.artt.mochaprofiles.R
import com.android.artt.mochaprofiles.profiles.ProfilesManager

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
                    mSharedPrefereces.edit().putBoolean("alternative_profiles_key", newValue as Boolean).apply()
                    ApplyProfileAsync().execute(context)
                    true
                }
                else -> true
            }

    private class ApplyProfileAsync: AsyncTask<Context, Any, Any>() {
        override fun doInBackground(vararg params: Context) {
            with (ProfilesManager(params[0])) {
                applyProfile(getSavedProfile())
                Log.d("MochaProfiles", "async completed")
            }
        }
    }
}