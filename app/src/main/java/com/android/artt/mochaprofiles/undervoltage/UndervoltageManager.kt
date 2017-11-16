package com.android.artt.mochaprofiles.undervoltage

import android.util.Log
import com.android.artt.mochaprofiles.utils.SU

class UndervoltageManager {
    companion object {
        val instance by lazy { UndervoltageManager() }
    }
    val TAG
        get() = "MochaProfiles"

    private val CPU_PATH
        get() = "/sys/kernel/debug/clock/vdd_core_offs"
    private val GPU_PATH
        get() = "/sys/kernel/debug/clock/vdd_gpu_offs"

    private val reduceCpuVoltageValue = -80
    private val reduceGpuVoltageValue = -45

    fun enableUndervolting(enabled: Boolean) {
        if (SU.instance.getSuAccess() && SU.instance.rootAccess()) {
            val cpuVoltageDeviation = if (enabled) reduceCpuVoltageValue else 0
            val gpuVoltageDeviation = if (enabled) reduceGpuVoltageValue else 0

            Log.d(TAG, "Undervoltage: enabled: $enabled\n cpu: $cpuVoltageDeviation mv\n gpu: $gpuVoltageDeviation mv")

            with(SU.instance) {
                writeFile(CPU_PATH, cpuVoltageDeviation)
                writeFile(GPU_PATH, gpuVoltageDeviation)

                close()
            }
        }
    }
}