package com.android.artt.mochaprofiles

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.android.artt.mochaprofiles.profiles.ProfilesManager
import com.android.artt.mochaprofiles.undervoltage.UndervoltageManager
import com.android.artt.mochaprofiles.utils.CommonUtils

class ProfilesBootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Intent.ACTION_BOOT_COMPLETED)
            return
        /*No need to check root cuz this functions will check it*/
        if (CommonUtils.instanse.isValidKernel) {
            with (ProfilesManager(context)) {
                applyProfile(getSavedProfile())
            }
            with(UndervoltageManager(context)) {
                enableUndervolting(isUndervoltingEnabled())
            }
        } else {
            showToast(context, R.string.invalid_kernel)
        }
    }


    private fun showToast(context: Context, stringResourceId: Int) {
        Toast.makeText(context, stringResourceId, Toast.LENGTH_LONG).show()
    }
}