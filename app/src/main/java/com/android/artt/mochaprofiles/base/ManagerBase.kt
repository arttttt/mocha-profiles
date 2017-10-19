package com.android.artt.mochaprofiles.base

import android.content.Context
import android.content.SharedPreferences

abstract class ManagerBase(private val context: Context) {

    abstract val PREFERENCES: String

    protected val mSharedPreferences: SharedPreferences by lazy { context.getSharedPreferences(PREFERENCES, Context.MODE_PRIVATE) }
}