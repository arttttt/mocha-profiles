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

        setTileStatus()
    }

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
            when (state) {
                Tile.STATE_ACTIVE -> {
                    mUndervoltManager.enableUndervolting(false)
                    icon = Icon.createWithResource(this@UndervoltTile, R.drawable.ic_undervolting_tile_off)
                    state = Tile.STATE_INACTIVE
                }
                Tile.STATE_INACTIVE -> {
                    mUndervoltManager.enableUndervolting((true))
                    icon = Icon.createWithResource(this@UndervoltTile, R.drawable.ic_undervolting_tile_on)
                    state = Tile.STATE_ACTIVE
                }
            }
            this.updateTile()
        }
    }

    private fun printDebugMessage(message: String) {
        Log.d(TAG, message)
    }
}