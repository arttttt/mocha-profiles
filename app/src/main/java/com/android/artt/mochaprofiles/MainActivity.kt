package com.android.artt.mochaprofiles;

import android.app.Activity;
import android.os.Bundle;

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
    }
}