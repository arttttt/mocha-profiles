package com.android.artt.mochaprofiles.tiles

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Icon
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.util.Log
import com.android.artt.mochaprofiles.R
import com.android.artt.mochaprofiles.profiles.Constants
import com.android.artt.mochaprofiles.profiles.ProfilesManager
import com.android.artt.mochaprofiles.utils.CommonUtils

class ProfilesTile : TileService() {

    val TAG = "MochaProfiles"

    private val mIconsMap by lazy { mapOf(ProfilesManager.LOW_PROFILE to Icon.createWithResource(this, R.drawable.ic_notification_profile_low),
            ProfilesManager.MIDDLE_PROFILE to Icon.createWithResource(this, R.drawable.ic_notification_profile_middle),
            ProfilesManager.HIGH_PROFILE to Icon.createWithResource(this, R.drawable.ic_notification_profile_high)) }
    private val mLabelsMap by lazy { mapOf(ProfilesManager.LOW_PROFILE to getString(R.string.low_profile_text),
            ProfilesManager.MIDDLE_PROFILE to getString(R.string.middle_profile_text),
            ProfilesManager.HIGH_PROFILE to getString(R.string.high_profile_text)) }
    private val mAltLabelsMap by lazy { mapOf(ProfilesManager.LOW_PROFILE to getString(R.string.low_profile_text_alt),
            ProfilesManager.MIDDLE_PROFILE to getString(R.string.middle_profile_text_alt),
            ProfilesManager.HIGH_PROFILE to getString(R.string.high_profile_text_alt)) }
    private val mTileState by lazy {
        when (CommonUtils.instance.isValidKernel) {
            true -> Tile.STATE_ACTIVE
            else -> Tile.STATE_UNAVAILABLE
        }
    }
    private val mSharedPreferences by lazy { getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE) }

    override fun onTileAdded() {
        super.onTileAdded()
        printDebugMessage("onTileAdded")

        with(ProfilesManager.instance) {
            val savedProfile = getSavedProfile()
            applyProfile(savedProfile, isAlternativeProfiles())
            updateTile(savedProfile)
            setProfilesEnabled(true)
        }
    }

    override fun onTileRemoved() {
        super.onTileRemoved()

        printDebugMessage("onTileRemoved")

        setProfilesEnabled(false)
    }

    override fun onStartListening() {
        super.onStartListening()
        printDebugMessage("onStartListening")

        updateTile(getSavedProfile())
    }

    override fun onClick() {
        super.onClick()
        printDebugMessage("onClick")

        /* Disable the tile while profile is applying */
        with(qsTile) {
            state = Tile.STATE_UNAVAILABLE
            updateTile()
        }

        val profile = when (getSavedProfile()) {
            ProfilesManager.LOW_PROFILE -> ProfilesManager.MIDDLE_PROFILE
            ProfilesManager.MIDDLE_PROFILE -> ProfilesManager.HIGH_PROFILE
            ProfilesManager.HIGH_PROFILE -> ProfilesManager.LOW_PROFILE
            else -> ProfilesManager.DEFAULT_PROFILE
        }
        ProfilesManager.instance.applyProfile(profile, isAlternativeProfiles())
        saveCurrentProfile(profile)
        updateTile(profile)
    }

    private fun updateTile(profile: String) {
        with(qsTile) {
            icon = mIconsMap[profile]
            label = if (isAlternativeProfiles()) mAltLabelsMap[profile] else mLabelsMap[profile]
            /* Disable tile if kernel is not valid or restore active state */
            state = mTileState
            updateTile()
        }
    }

    private fun setProfilesEnabled(enabled: Boolean) {
        mSharedPreferences.edit().putBoolean(Constants.PROFILES_ENABLED_KEY, enabled).apply()
    }

    private fun isAlternativeProfiles(): Boolean = mSharedPreferences.getBoolean(Constants.PROFILES_ALTERNATIVE_KEY, false)

    private fun getSavedProfile(): String = mSharedPreferences.getString(Constants.PROFILE_KEY, ProfilesManager.DEFAULT_PROFILE)

    private fun saveCurrentProfile(profile: String) {
        mSharedPreferences.edit().putString(Constants.PROFILE_KEY, profile).apply()
    }

    private fun printDebugMessage(msg: String) {
        Log.d(TAG, msg)
    }
}