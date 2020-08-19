package com.hjhjw1991.stark.plugin

import android.content.Intent

import android.os.Bundle


interface ActivityResults {
    fun register(listener: Listener?)
    fun unregister(listener: Listener?): Boolean
    fun startActivityForResult(intent: Intent?, requestCode: Int)
    fun startActivityForResult(
        intent: Intent?,
        requestCode: Int,
        options: Bundle?
    )

    interface Listener {
        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    }
}
