package com.android.artt.mochaprofiles

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import com.android.artt.mochaprofiles.settings.SettingsFragment

class MainActivity : Activity() {

    @SuppressLint("CommitTransaction")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        with(fragmentManager.beginTransaction()) {
            add(R.id.fragment_container, SettingsFragment()).commit()
        }
    }
}
