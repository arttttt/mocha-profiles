package com.android.artt.mochaprofiles.tiles

import android.graphics.drawable.Icon
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.util.Log
import com.android.artt.mochaprofiles.R
import com.android.artt.mochaprofiles.undervolting.UndervoltManager

class UndervoltageTile : TileService() {
    val TAG = "MochaProfiles"
    private val mUndervoltManager by lazy { UndervoltManager(this) }
    private val mTileParams by lazy { mapOf(Tile.STATE_ACTIVE to Pair(Tile.STATE_INACTIVE, false),
            Tile.STATE_INACTIVE to Pair(Tile.STATE_ACTIVE, true)) }
    private val mTileIcon by lazy { Icon.createWithResource(this, R.drawable.ic_undervolting_tile) }

    override fun onStartListening() {
        super.onStartListening()
        printDebugMessage("onStartListening und")

        setTileStatus()
    }

    override fun onClick() {
        super.onClick()
        printDebugMessage("onClick und")

        updateTile()
    }

    private fun setTileStatus() {
        with (qsTile) {
            state = if (mUndervoltManager.isUndervoltingEnabled()) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
            this.updateTile()
        }
    }

    private fun updateTile() {
        with (qsTile) {
            icon = mTileIcon
            mTileParams[state]?.let {
                state = it.first
                mUndervoltManager.enableUndervolting(it.second)
            }
            this.updateTile()
        }
    }

    private fun printDebugMessage(message: String) {
        Log.d(TAG, message)
    }
}