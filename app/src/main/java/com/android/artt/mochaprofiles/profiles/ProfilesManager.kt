package com.android.artt.mochaprofiles.profiles

import android.util.Log
import com.android.artt.mochaprofiles.base.ProfileBase
import com.android.artt.mochaprofiles.profiles.classic.ProfileHigh
import com.android.artt.mochaprofiles.profiles.classic.ProfileLow
import com.android.artt.mochaprofiles.profiles.classic.ProfileMiddle
import com.android.artt.mochaprofiles.profiles.classic.ProfileSuspend
import com.android.artt.mochaprofiles.profiles.alternative.ProfileLowAlt
import com.android.artt.mochaprofiles.profiles.alternative.ProfileMiddleAlt
import com.android.artt.mochaprofiles.profiles.alternative.ProfileHighAlt
import com.android.artt.mochaprofiles.utils.SU
import java.io.File
import java.io.FileFilter
import java.util.regex.Pattern

class ProfilesManager {
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

        val instance by lazy { ProfilesManager() }
    }

    val TAG
        get() = "MochaProfiles"

    private val CORE_ONLINE = "/sys/devices/system/cpu/cpu%d/online"

    private val mProfilesMap by lazy { mapOf(LOW_PROFILE to ProfileLow(),
            MIDDLE_PROFILE to ProfileMiddle(),
            HIGH_PROFILE to ProfileHigh(),
            SUSPEND_PROFILE to ProfileSuspend()) }

    private val mAltProfilesMap by lazy { mapOf(LOW_PROFILE to ProfileLowAlt(),
            MIDDLE_PROFILE to ProfileMiddleAlt(),
            HIGH_PROFILE to ProfileHighAlt()) }

    private fun setProfile(profile: String, useAltProfiles: Boolean) {
        if (useAltProfiles)
            mAltProfilesMap[profile]?.let {
                if (SU.instance.getSuAccess() && SU.instance.rootAccess()) {
                    (it as ProfileBase).commonParams.forEach { path, value ->
                        SU.instance.writeFile(path, value)
                    }
                    (it as ProfileBase.intelliactiveGovernor).intelliactiveParams.forEach { path, value ->
                        SU.instance.writeFile(path, value)
                    }
                }
                wakeAllCores()

                Log.d(TAG, "applied profile: $profile")
                SU.instance.close()
            }
        else
            mProfilesMap[profile]?.let {
                if (SU.instance.getSuAccess() && SU.instance.rootAccess()) {
                    it.commonParams.forEach { path, value ->
                        SU.instance.writeFile(path, value)
                    }
                    when (it) {
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

                    Log.d(TAG, "applied profile: $profile")
                    SU.instance.close()
                }
            }
    }

    fun applyProfile(profile: String, useAltProfiles: Boolean) {
            setProfile(profile, useAltProfiles)
    }

    private fun wakeAllCores() {
        var x = 0
        while (x < getCoresCount()) {
            SU.instance.writeFile(String.format(CORE_ONLINE, x), 1)
            ++x
        }
    }

    private fun getCoresCount(): Int {
        var sLastCpuCoreCount = -1

        try {
            with(File("/sys/devices/system/cpu/")) {
                val files = listFiles(FileFilter
                { pathname ->
                    if (Pattern.matches("cpu[0-9]", pathname.name)) {
                        return@FileFilter true
                    }
                    false
                })

                sLastCpuCoreCount = files.size
            }
        } catch (e: Exception) {
            sLastCpuCoreCount = Runtime.getRuntime().availableProcessors()
        }

        return sLastCpuCoreCount
    }
}