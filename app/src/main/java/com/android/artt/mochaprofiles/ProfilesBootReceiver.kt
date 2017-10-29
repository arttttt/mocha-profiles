package com.android.artt.mochaprofiles

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.android.artt.mochaprofiles.profiles.ProfilesManager
import com.android.artt.mochaprofiles.undervoltage.UndervoltageManager
import com.android.artt.mochaprofiles.utils.CommonUtils

class ProfilesBootReceiver : BroadcastReceiver() {

    val TAG
        get() = "MochaProfiles"

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Intent.ACTION_BOOT_COMPLETED)
            return
        /*No need to check root cuz this functions will check it*/
        if (CommonUtils.instance.isValidKernel) {
            with (ProfilesManager(context)) {
                if (mProfilesEnabled)
                    applyProfile(getSavedProfile())
                else
                    Log.d(TAG, "profiles disabled")
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