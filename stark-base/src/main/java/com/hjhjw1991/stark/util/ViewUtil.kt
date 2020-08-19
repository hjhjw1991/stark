package com.hjhjw1991.stark.util

import android.app.Activity
import android.view.View
import android.widget.FrameLayout

/**
 * @author huangjun.barney
 * @since 2020/7/12
 */

object ViewUtil {
    @JvmStatic
     fun getActivityRoot(activity: Activity?): FrameLayout? {
        if (activity == null) {
            return null
        }
        try {
            return activity.window.decorView
                .findViewById<View>(android.R.id.content) as FrameLayout
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}