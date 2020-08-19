package com.hjhjw1991.stark.framework

import android.app.Activity
import com.hjhjw1991.stark.plugin.ForegroundManager
import com.hjhjw1991.stark.plugin.OnApplicationBackgroundListener
import com.hjhjw1991.stark.plugin.OnApplicationForegroundListener
import javax.inject.Inject


@AppScope
 class ForegroundManagerImpl @Inject constructor() :
    ForegroundManager {
    private val foregroundListeners: MutableList<OnApplicationForegroundListener> =
        ArrayList()
    private val backgroundListeners: MutableList<OnApplicationBackgroundListener> =
        ArrayList()
    override var foregroundActivity: Activity? = null
    override var isApplicationForeground = false

    override fun addOnApplicationForegroundListener(listener: OnApplicationForegroundListener) {
        foregroundListeners.add(listener)
    }

    override fun removeOnApplicationForegroundListener(listener: OnApplicationForegroundListener): Boolean {
        return foregroundListeners.remove(listener)
    }

    override fun addOnApplicationBackgroundListener(listener: OnApplicationBackgroundListener) {
        backgroundListeners.add(listener)
    }

    override fun removeOnApplicationBackgroundListener(listener: OnApplicationBackgroundListener): Boolean {
        return backgroundListeners.remove(listener)
    }

    fun notifyAppForegrounded(activity: Activity) {
        for (listener in foregroundListeners) {
            listener.onApplicationForeground(activity.applicationContext)
        }
    }

    fun notifyAppBackgrounded(activity: Activity) {
        for (listener in backgroundListeners) {
            listener.onApplicationBackground(activity.applicationContext)
        }
    }
}
