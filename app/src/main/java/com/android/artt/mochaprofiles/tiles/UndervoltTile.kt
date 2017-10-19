package com.android.artt.mochaprofiles.tiles

import android.graphics.drawable.Icon
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService
import android.util.Log
import com.android.artt.mochaprofiles.R
import com.android.artt.mochaprofiles.undervolting.UndervoltManager

class UndervoltTile : TileService() {
    val TAG = "MochaProfiles"
    private val mUndervoltManager by lazy { UndervoltManager(this) }

    override fun onTileAdded() {
        super.onTileAdded()
        printDebugMessage("onTileAdded und")

        val tile = qsTile

        tile.state = when (mUndervoltManager.isUndervoltingEnabled()) {
            true -> Tile.STATE_ACTIVE
            else -> Tile.STATE_INACTIVE
        }
        tile.updateTile()
    }

    override fun onStartListening() {
        super.onStartListening()
        printDebugMessage("onStartListening und")

        val tile = qsTile

        tile.state = when (mUndervoltManager.isUndervoltingEnabled()) {
            true -> Tile.STATE_ACTIVE
            else -> Tile.STATE_INACTIVE
        }
        tile.updateTile()
    }

    override fun onClick() {
        super.onClick()
        printDebugMessage("onClick und")

        val tile = qsTile

        when (tile.state) {
            Tile.STATE_ACTIVE -> {
                mUndervoltManager.enableUndervolting(false)
                tile.icon = Icon.createWithResource(this, R.drawable.ic_undervolting_tile_off)
                tile.state = Tile.STATE_INACTIVE
            }
            Tile.STATE_INACTIVE -> {
                mUndervoltManager.enableUndervolting((true))
                tile.icon = Icon.createWithResource(this, R.drawable.ic_undervolting_tile_on)
                tile.state = Tile.STATE_ACTIVE
            }
        }
        tile.updateTile()
    }

    private fun printDebugMessage(message: String) {
        Log.d(TAG, message)
    }
}