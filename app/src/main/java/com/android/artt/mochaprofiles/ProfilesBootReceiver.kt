package com.android.artt.mochaprofiles

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.android.artt.mochaprofiles.profiles.ProfilesManager
import com.android.artt.mochaprofiles.undervoltage.UndervoltageManager
import com.android.artt.mochaprofiles.utils.SU

class ProfilesBootReceiver : BroadcastReceiver() {

    private val VALID_KERNEL_NAME = "smoker24.1"
    private val KERNEL_VERSION_PATH = "/proc/version"

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Intent.ACTION_BOOT_COMPLETED)
            return
        if (!SU.instance.getSuAccess() || !SU.instance.rootAccess()) {
            showToast(context, R.string.no_root)
            return
        }
        if (isValidKernel()) {
            with (ProfilesManager(context)) {
                applyProfile(getSavedProfile())
            }
            with(UndervoltageManager(context)) {
                enableUndervolting(isUndervoltingEnabled())
            }
        } else {
            showToast(context, R.string.invalid_kernel)
        }
        SU.instance.close()
    }


    private fun showToast(context: Context, stringResourceId: Int) {
        Toast.makeText(context, stringResourceId, Toast.LENGTH_LONG).show()
    }

    private fun isValidKernel(): Boolean =
            SU.instance.readFile(KERNEL_VERSION_PATH).contains(VALID_KERNEL_NAME, true)
}