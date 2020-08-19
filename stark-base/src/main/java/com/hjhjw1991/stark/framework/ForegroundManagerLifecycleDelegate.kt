package com.hjhjw1991.stark.framework

import android.app.Activity
import android.os.Bundle
import com.hjhjw1991.stark.util.StarkGlobalSp
import javax.inject.Inject


@AppScope
class ForegroundManagerLifecycleDelegate @Inject constructor(private val foregroundManager: ForegroundManagerImpl) :
    StarkLifecycleDelegate() {
    private var activityCount = 0
    private val mListener =
        StarkFloatingViewManager.StarkFloatingViewListener()

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        // 在每个Activity创建时注册监听器
        if (StarkGlobalSp.isStarkFloatingViewEnabled()) {
            StarkFloatingViewManager.get()
                ?.add()?.apply {
                    listener(mListener)
                }
        }
    }
    override fun onActivityResumed(activity: Activity?) {
        foregroundManager.foregroundActivity = activity
    }

    override fun onActivityPaused(activity: Activity?) {
        val foregroundActivity = foregroundManager.foregroundActivity
        if (foregroundActivity == activity) {
            foregroundManager.foregroundActivity = null
        }
        StarkFloatingViewManager.get()
            ?.detach(activity)
    }

    override fun onActivityStarted(activity: Activity?) {
        if (!appIsForegrounded()) {
            foregroundManager.notifyAppForegrounded(activity!!)
            activityCount = 0
        }
        activityCount++
        foregroundManager.isApplicationForeground = appIsForegrounded()
        // 在每个Activity attach调试悬浮窗
        mListener.context = activity
        if (StarkGlobalSp.isStarkFloatingViewEnabled()) {
            StarkFloatingViewManager.get()
                ?.attach(activity)
        }
    }

    override fun onActivityStopped(activity: Activity?) {
        activityCount--
        if (!appIsForegrounded()) {
            activity?.let { foregroundManager.notifyAppBackgrounded(it) }
            activityCount = 0
        }
        foregroundManager.isApplicationForeground = appIsForegrounded()
    }

    private fun appIsForegrounded(): Boolean {
        return activityCount > 0
    }

}
