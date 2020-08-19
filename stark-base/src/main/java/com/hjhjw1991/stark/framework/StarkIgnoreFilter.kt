package com.hjhjw1991.stark.framework

import android.app.Activity
import android.app.Application

import android.os.Bundle
import android.support.v4.util.SimpleArrayMap
import com.hjhjw1991.stark.plugin.StarkIgnore
import com.hjhjw1991.stark.util.StarkGlobalSp
import javax.inject.Inject


@AppScope
class StarkIgnoreFilter @Inject constructor(val lifecycle: StarkLifecycle) :
    Application.ActivityLifecycleCallbacks {
    private val cache: SimpleArrayMap<Class<*>, Boolean> = SimpleArrayMap(5)
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        if (!shouldIgnore(activity)) {
            lifecycle.onActivityCreated(activity, savedInstanceState)
        }
    }

    override fun onActivityStarted(activity: Activity) {
        if (!shouldIgnore(activity)) {
            lifecycle.onActivityStarted(activity)
        }
    }

    override fun onActivityResumed(activity: Activity) {
        if (!shouldIgnore(activity)) {
            lifecycle.onActivityResumed(activity)
        }
    }

    override fun onActivityPaused(activity: Activity) {
        if (!shouldIgnore(activity)) {
            lifecycle.onActivityPaused(activity)
        }
    }

    override fun onActivityStopped(activity: Activity) {
        if (!shouldIgnore(activity)) {
            lifecycle.onActivityStopped(activity)
        }
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {
        if (!shouldIgnore(activity)) {
            lifecycle.onActivitySaveInstanceState(activity, outState)
        }
    }

    override fun onActivityDestroyed(activity: Activity) {
        if (!shouldIgnore(activity)) {
            lifecycle.onActivityDestroyed(activity)
        }
    }

    private fun shouldIgnore(activity: Activity): Boolean {
        if (!StarkGlobalSp.isStarkEnabled()) {
            return true
        }
        val clz: Class<*> = activity.javaClass
        return (if (cache.containsKey(clz)) {
            cache.get(clz)
        } else {
            val ignore: StarkIgnore? = activity.javaClass.getAnnotation(
                StarkIgnore::class.java
            )
            val shouldIgnore = ignore != null
            cache.put(clz, shouldIgnore)
            shouldIgnore
        }) as Boolean
    }

}