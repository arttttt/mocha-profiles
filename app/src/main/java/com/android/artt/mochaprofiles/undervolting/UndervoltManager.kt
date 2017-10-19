package com.android.artt.mochaprofiles.undervolting

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.android.artt.mochaprofiles.utils.SU

class UndervoltManager(context: Context) {
    val TAG = "MochaProfiles"

    private val CPU_PATH = "/sys/kernel/debug/clock/vdd_core_offs"
    private val GPU_PATH = "/sys/kernel/debug/clock/vdd_gpu_offs"

    private val reduceCpuVoltageValue = -80
    private val reduceGpuVoltageValue = -45

    val PREFERENCES = "undervolt_preferences"
    val UNDERVOLT_KEY = "enabled"

    private val mSharedPreferences: SharedPreferences

    init {
        mSharedPreferences = context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE)
    }

    @SuppressLint("CommitPrefEdits")
    fun enableUndervolting(enabled: Boolean) {
        if (SU.instance.getSuAccess() && SU.instance.rootAccess()) {
            with(mSharedPreferences.edit()) {
                putBoolean(UNDERVOLT_KEY, enabled)
                apply()
            }

            val cpuVoltageDeviation = when (enabled) {
                true -> reduceCpuVoltageValue
                else -> 0
            }
            val gpuVoltageDeviation = when (enabled) {
                true -> reduceGpuVoltageValue
                else -> 0
            }

            Log.d(TAG, "cpu und: $cpuVoltageDeviation gpu und: $gpuVoltageDeviation enabled: $enabled")

            with(SU.instance) {
                writeFile(CPU_PATH, cpuVoltageDeviation)
                writeFile(GPU_PATH, gpuVoltageDeviation)

                close()
            }
        }
    }

    fun isUndervoltingEnabled(): Boolean = mSharedPreferences.getBoolean(UNDERVOLT_KEY, false)
}