package com.android.artt.mochaprofiles.profiles

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.android.artt.mochaprofiles.utils.SU

class ProfilesManager(context: Context) {
    companion object {
        val LOW_PROFILE: String = "low"
        val MIDDLE_PROFILE: String = "middle"
        val HIGH_PROFILE: String = "high"
        val VIDEO_PROFILE: String = "video"
        val SUSPEND_PROFILE: String = "suspend"
        val DEFAULT_PROFILE: String
            get() = MIDDLE_PROFILE
    }

    val TAG = "MochaProfiles"

    val PREFERENCES = "profiles_preferences"
    val PROFILE_KEY = "profile"

    private val mSharedPreferences: SharedPreferences
    private val mProfilesMap: Map<String, ProfileBase>

    init {
        mSharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
        mProfilesMap = mapOf(LOW_PROFILE to ProfileLow(),
                MIDDLE_PROFILE to ProfileMiddle(),
                HIGH_PROFILE to ProfileHigh(),
                VIDEO_PROFILE to ProfileVideo(),
                SUSPEND_PROFILE to ProfileSuspend())
    }

    private fun setProfile(profile: String): Boolean {
        var result = false
        mProfilesMap[profile]?.let {
            if (SU.instance.getSuAccess() && SU.instance.rootAccess()) {
                it.commonParams.forEach { path, value ->
                    SU.instance.writeFile(path, value)
                }

                when(it) {
                    is ProfileBase.smartmaxGovernor -> {
                        it.smartmaxParams.forEach { path, value ->
                            SU.instance.writeFile(path, value)
                        }
                    }
                    is ProfileBase.interactiveGovernor -> {
                        it.interactiveParams.forEach { path, value ->
                            SU.instance.writeFile(path, value)
                        }
                    }
                }

                result = true
            }
            SU.instance.close()
        }
        return result
    }

    fun getSavedProfile(): String {
        return mSharedPreferences.getString(PROFILE_KEY, DEFAULT_PROFILE)
    }

    fun applyProfile(profile: String, save: Boolean = false): Boolean {
        Log.d(TAG, "applied profile: $profile")
        if (save) {
            val edit = mSharedPreferences.edit()
            edit.putString(PROFILE_KEY, profile)
            edit.apply()
        }
        return setProfile(profile)
    }
}