package com.hjhjw1991.stark

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.hjhjw1991.stark.util.StarkGlobalSp

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        StarkGlobalSp.putBoolean(StarkGlobalSp.STARK_PROP_ENABLE, true)
        StarkGlobalSp.putBoolean(StarkGlobalSp.STARK_PROP_ENABLE_SERVICE, true)
        StarkGlobalSp.putBoolean(StarkGlobalSp.STARK_PROP_ENABLE_FLOATING_VIEW, true)
    }
}
