package com.hjhjw1991.stark.plugin

import android.app.Activity


interface ForegroundManager {
    val foregroundActivity: Activity?
    val isApplicationForeground: Boolean

    fun addOnApplicationForegroundListener(listener: OnApplicationForegroundListener)
    fun removeOnApplicationForegroundListener(listener: OnApplicationForegroundListener): Boolean
    fun addOnApplicationBackgroundListener(listener: OnApplicationBackgroundListener)
    fun removeOnApplicationBackgroundListener(listener: OnApplicationBackgroundListener): Boolean
}
