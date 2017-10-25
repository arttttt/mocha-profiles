package com.android.artt.mochaprofiles.utils

class CommonUtils {
    companion object {
        val instanse by lazy { CommonUtils() }
    }

    private val VALID_KERNEL_NAME = "smoker24.1"
    private val KERNEL_VERSION_PATH = "/proc/version"

    val isValidKernel by lazy { SU.instance.readFile(KERNEL_VERSION_PATH).contains(VALID_KERNEL_NAME, true) }
}