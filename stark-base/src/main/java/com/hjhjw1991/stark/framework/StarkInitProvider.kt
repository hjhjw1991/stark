package com.hjhjw1991.stark.framework

import android.app.Application
import android.content.Context
import android.util.Log

/**
 * @author huangjun.barney
 * @since 2020-05-15
 */
class StarkInitProvider : EmptyContentProvider() {
    override fun onCreate(): Boolean {
        return try {
            val context = requireContext()
            Stark.setApplication(context)
            val application: Application = context.applicationContext as Application
            val component: AppComponent = AppComponent.getInstance(context)
            application.registerActivityLifecycleCallbacks(component.activityLifecycleCallbacks)
            true
        } catch (e: Exception) {
            Log.e("Stark", "Init failed. ${e.toString()}")
            false
        }
    }

    private fun requireContext(): Context {
        return this.context ?: throw NullPointerException("context == null")
    }
}