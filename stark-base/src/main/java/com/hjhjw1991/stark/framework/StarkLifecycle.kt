package com.hjhjw1991.stark.framework

import android.app.Activity
import android.app.Application

import android.os.Bundle
import javax.inject.Inject

/**
 * @author huangjun.barney
 * @since 2020-05-15
 */
@AppScope
class StarkLifecycle @Inject constructor(private val delegates: Set<@JvmSuppressWildcards StarkLifecycleDelegate>) :
    Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        for (delegate in delegates) {
            delegate.onActivityCreated(activity, savedInstanceState)
        }
    }

    override fun onActivityStarted(activity: Activity?) {
        for (delegate in delegates) {
            delegate.onActivityStarted(activity)
        }
    }

    override fun onActivityResumed(activity: Activity?) {
        for (delegate in delegates) {
            delegate.onActivityResumed(activity)
        }
    }

    override fun onActivityPaused(activity: Activity?) {
        for (delegate in delegates) {
            delegate.onActivityPaused(activity)
        }
    }

    override fun onActivityStopped(activity: Activity?) {
        for (delegate in delegates) {
            delegate.onActivityStopped(activity)
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        for (delegate in delegates) {
            delegate.onActivitySaveInstanceState(activity, outState)
        }
    }

    override fun onActivityDestroyed(activity: Activity?) {
        for (delegate in delegates) {
            delegate.onActivityDestroyed(activity)
        }
    }

}