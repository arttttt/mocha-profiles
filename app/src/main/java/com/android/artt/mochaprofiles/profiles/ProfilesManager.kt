package com.android.artt.mochaprofiles.profiles

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.android.artt.mochaprofiles.base.ManagerBase
import com.android.artt.mochaprofiles.utils.SU

class ProfilesManager(context: Context) : ManagerBase(context) {
    companion object {
        val LOW_PROFILE
            get() = "low"
        val MIDDLE_PROFILE
            get() = "middle"
        val HIGH_PROFILE
            get() = "high"
        val SUSPEND_PROFILE
            get() = "suspend"
        val DEFAULT_PROFILE
            get() = MIDDLE_PROFILE
    }

    val TAG
        get() = "MochaProfiles"

    override val PREFERENCES
        get() = "profiles_preferences"
    val PROFILE_KEY
        get() = "profile"
    val PROFILES_ENABLED_KEY
        get() = "profiles_enabled"

    var mProfilesEnabled: Boolean
        get() {
            return mSharedPreferences.getBoolean(PROFILES_ENABLED_KEY, false)
        }
        @SuppressLint("CommitPrefEdits")
        set(value) {
            with(mSharedPreferences.edit()) {
                putBoolean(PROFILES_ENABLED_KEY, value)
                apply()
            }
        }

    private val mProfilesMap by lazy { mapOf(LOW_PROFILE to ProfileLow(),
            MIDDLE_PROFILE to ProfileMiddle(),
            HIGH_PROFILE to ProfileHigh(),
            SUSPEND_PROFILE to ProfileSuspend()) }

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
                Log.d(TAG, "applied profile: $profile")
            }
            SU.instance.close()
        }
        return result
    }

    fun getSavedProfile(): String = mSharedPreferences.getString(PROFILE_KEY, DEFAULT_PROFILE)

    @SuppressLint("CommitPrefEdits")
    fun applyProfile(profile: String, save: Boolean = false) {
        if (!setProfile(profile))
            return

        if (save) {
            with(mSharedPreferences.edit()) {
                putString(PROFILE_KEY, profile)
                apply()
            }
        }
    }
}