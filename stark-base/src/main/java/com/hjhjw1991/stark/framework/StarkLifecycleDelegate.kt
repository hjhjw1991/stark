package com.hjhjw1991.stark.framework

import android.app.Activity
import android.app.Application
import android.os.Bundle

/**
 * @author huangjun.barney
 * @since 2020-05-15
 */

abstract class StarkLifecycleDelegate : Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {}
    override fun onActivityStarted(activity: Activity?) {}
    override fun onActivityResumed(activity: Activity?) {}
    override fun onActivityPaused(activity: Activity?) {}
    override fun onActivityStopped(activity: Activity?) {}
    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {}
    override fun onActivityDestroyed(activity: Activity?) {}
}