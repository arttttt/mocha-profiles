package com.android.artt.mochaprofiles.undervolting

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.android.artt.mochaprofiles.base.ManagerBase
import com.android.artt.mochaprofiles.utils.SU

class UndervoltManager(context: Context) : ManagerBase(context) {
    val TAG
        get() = "MochaProfiles"

    private val CPU_PATH
        get() = "/sys/kernel/debug/clock/vdd_core_offs"
    private val GPU_PATH
        get() = "/sys/kernel/debug/clock/vdd_gpu_offs"

    private val reduceCpuVoltageValue = -80
    private val reduceGpuVoltageValue = -45

    override val PREFERENCES
        get() = "undervolt_preferences"
    val UNDERVOLT_KEY
        get() = "enabled"

    @SuppressLint("CommitPrefEdits")
    fun enableUndervolting(enabled: Boolean) {
        if (SU.instance.getSuAccess() && SU.instance.rootAccess()) {
            if (isUndervoltingEnabled() != enabled) {
                with(mSharedPreferences.edit()) {
                    putBoolean(UNDERVOLT_KEY, enabled)
                    apply()
                }
            }

            val cpuVoltageDeviation = if (enabled) reduceCpuVoltageValue else 0
            val gpuVoltageDeviation = if (enabled) reduceGpuVoltageValue else 0

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