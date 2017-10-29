package com.android.artt.mochaprofiles.tiles

import android.graphics.drawable.Icon
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.util.Log
import com.android.artt.mochaprofiles.R
import com.android.artt.mochaprofiles.profiles.ProfilesManager
import com.android.artt.mochaprofiles.utils.CommonUtils

class ProfilesTile : TileService() {

    val TAG = "MochaProfiles"

    private val mProfileManager by lazy { ProfilesManager(this) }
    private val mIconsMap by lazy { mapOf(ProfilesManager.LOW_PROFILE to Icon.createWithResource(this, R.drawable.ic_notification_profile_low),
            ProfilesManager.MIDDLE_PROFILE to Icon.createWithResource(this, R.drawable.ic_notification_profile_middle),
            ProfilesManager.HIGH_PROFILE to Icon.createWithResource(this, R.drawable.ic_notification_profile_high)) }
    private val mLabelsMap by lazy { mapOf(ProfilesManager.LOW_PROFILE to getString(R.string.low_profile_text),
            ProfilesManager.MIDDLE_PROFILE to getString(R.string.middle_profile_text),
            ProfilesManager.HIGH_PROFILE to getString(R.string.high_profile_text)) }
    private val mTileState by lazy {
        when (CommonUtils.instance.isValidKernel) {
            true -> Tile.STATE_ACTIVE
            else -> Tile.STATE_UNAVAILABLE
        }
    }

    override fun onTileAdded() {
        super.onTileAdded()
        printDebugMessage("onTileAdded")

        mProfileManager.mProfilesEnabled = true
        updateTile(mProfileManager.getSavedProfile())
    }

    override fun onTileRemoved() {
        super.onTileRemoved()

        printDebugMessage("onTileRemoved")

        mProfileManager.mProfilesEnabled = false
    }

    override fun onStartListening() {
        super.onStartListening()
        printDebugMessage("onStartListening")

        updateTile(mProfileManager.getSavedProfile())
    }

    override fun onClick() {
        super.onClick()
        printDebugMessage("onClick")

        /* Disable the tile while profile is applying */
        with(qsTile) {
            state = Tile.STATE_UNAVAILABLE
            updateTile()
        }

        val profile = when (mProfileManager.getSavedProfile()) {
            ProfilesManager.LOW_PROFILE -> ProfilesManager.MIDDLE_PROFILE
            ProfilesManager.MIDDLE_PROFILE -> ProfilesManager.HIGH_PROFILE
            ProfilesManager.HIGH_PROFILE -> ProfilesManager.LOW_PROFILE
            else -> ProfilesManager.DEFAULT_PROFILE
        }
        mProfileManager.applyProfile(profile, true)
        updateTile(profile)
    }

    private fun updateTile(profile: String) {
        with(qsTile) {
            icon = mIconsMap[profile]
            label = mLabelsMap[profile]
            /* Disable tile if kernel is not valid or restore active state */
            state = mTileState
            updateTile()
        }
    }

    private fun printDebugMessage(msg: String) {
        Log.d(TAG, msg)
    }
}