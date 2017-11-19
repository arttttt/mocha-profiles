package com.android.artt.mochaprofiles

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import com.android.artt.mochaprofiles.settings.SettingsFragment

class MainActivity : AppCompatActivity() {

    @SuppressLint("CommitTransaction")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        toolbar.setLogo(R.mipmap.ic_launcher)
        setSupportActionBar(toolbar)

        fragmentManager.beginTransaction().add(R.id.fragment_container, SettingsFragment()).commit()
    }
}
