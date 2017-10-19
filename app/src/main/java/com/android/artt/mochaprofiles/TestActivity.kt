package com.android.artt.mochaprofiles;

import android.app.Activity;
import android.content.Intent;
import android.os.Build
import android.os.Bundle;

class TestActivity: Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
    }
}
