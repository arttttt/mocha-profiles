package com.android.artt.mochaprofiles.tiles

import android.content.Context
import android.graphics.drawable.Icon
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.util.Log
import com.android.artt.mochaprofiles.R
import com.android.artt.mochaprofiles.profiles.Constants
import com.android.artt.mochaprofiles.undervoltage.UndervoltageManager
import com.android.artt.mochaprofiles.utils.CommonUtils

class UndervoltageTile : TileService() {
    val TAG = "MochaProfiles"
    private val mTileParams by lazy { mapOf(Tile.STATE_ACTIVE to Pair(Tile.STATE_INACTIVE, false),
            Tile.STATE_INACTIVE to Pair(Tile.STATE_ACTIVE, true)) }
    private val mTileIcon by lazy { Icon.createWithResource(this, R.drawable.ic_undervolting_tile) }
    private val mSharedPreferences by lazy { getSharedPreferences(Constants.PREFERENCES, Context.MODE_PRIVATE) }

    override fun onTileRemoved() {
        super.onTileRemoved()
        printDebugMessage("onTileRemoved undervoltage")

        UndervoltageManager.instance.enableUndervolting(false)
    }

    override fun onStartListening() {
        super.onStartListening()
        printDebugMessage("onStartListening undervoltage")

        setTileStatus()
    }

    override fun onClick() {
        super.onClick()
        printDebugMessage("onClick undervoltage")

        updateTile()
    }

    private fun setTileStatus() {
        with (qsTile) {
            state = when (CommonUtils.instance.isValidKernel) {
                true -> if (isUndervoltingEnabled()) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
                else -> Tile.STATE_UNAVAILABLE
            }
            this.updateTile()
        }
    }

    private fun updateTile() {
        with (qsTile) {
            icon = mTileIcon
            mTileParams[state]?.let {
                state = it.first
                UndervoltageManager.instance.enableUndervolting(it.second)
            }
            this.updateTile()
        }
    }

    fun isUndervoltingEnabled(): Boolean = mSharedPreferences.getBoolean(Constants.UNDERVOLT_KEY, false)

    private fun printDebugMessage(message: String) {
        Log.d(TAG, message)
    }
}