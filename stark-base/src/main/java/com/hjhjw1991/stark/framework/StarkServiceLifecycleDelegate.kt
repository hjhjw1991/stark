package com.hjhjw1991.stark.framework

import android.app.Activity
import android.content.Context
import android.content.Intent
import javax.inject.Inject


/**
 * @author huangjun.barney
 * @since 2020-05-15
 */

@AppScope
class StarkServiceLifecycleDelegate @Inject constructor(val container: CoreComponentContainer) :
    StarkLifecycleDelegate() {
    private var foregroundActivity: Activity? = null
    override fun onActivityResumed(activity: Activity?) {
        foregroundActivity = activity
        val component = container.getComponent(activity) ?: return
        component.serviceConnection?.let {
            foregroundActivity?.bindService(
                Intent(activity, StarkService::class.java),
                it,
                Context.BIND_AUTO_CREATE
            )
        }
        component.menuController.onResume()
    }

    override fun onActivityPaused(activity: Activity?) {
        if (foregroundActivity === activity) {
            val component = container.getComponent(activity) ?: return
            component.serviceConnection?.let {
                foregroundActivity?.unbindService(it)
            }
            component.menuController.onPause()
            foregroundActivity = null
        }
    }

}