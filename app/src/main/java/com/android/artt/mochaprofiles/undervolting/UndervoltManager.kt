package com.android.artt.mochaprofiles.undervolting

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.android.artt.mochaprofiles.utils.SU

class UndervoltManager(context: Context) {
    val TAG = "MochaProfiles"

    private val CPU_PATH = "/sys/kernel/debug/clock/vdd_core_offs"
    private val GPU_PATH = "/sys/kernel/debug/clock/vdd_gpu_offs"

    private val cpuTestValue = -80
    private val gpuTestValue = -45

    val PREFERENCES = "undervolt_preferences"
    val UNDERVOLT_KEY = "enabled"

    private val mSharedPreferences: SharedPreferences

    init {
        mSharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
    }

    fun enableUndervolting(enabled: Boolean) {
        if (SU.instance.getSuAccess() && SU.instance.rootAccess()) {
            with(mSharedPreferences.edit()) {
                putBoolean(UNDERVOLT_KEY, enabled)
                apply()
            }
            Log.d(TAG, "cpu und: $cpuTestValue gpu und: $gpuTestValue enabled: $enabled")
            if (enabled) {
                SU.instance.writeFile(CPU_PATH, cpuTestValue)
                SU.instance.writeFile(GPU_PATH, gpuTestValue)
            }
            else {
                SU.instance.writeFile(CPU_PATH, 0)
                SU.instance.writeFile(GPU_PATH, 0)
            }
            SU.instance.close()
        }
    }

    fun isUndervoltingEnabled(): Boolean {
        return mSharedPreferences.getBoolean(UNDERVOLT_KEY, false)
    }
}