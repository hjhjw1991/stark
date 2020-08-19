package com.hjhjw1991.stark

import android.app.Application
import android.content.Context

class Application : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        // do what you want
    }
    override fun onCreate() {
        super.onCreate()
        //  do what you want
    }

}