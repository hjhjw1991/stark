package com.hjhjw1991.stark.framework

import android.app.Activity
import android.app.Application
import android.content.Context


/**
 * singleton stark no op
 * [open] to open debugger
 * @author huangjun.barney
 * @since 2020-05-12
 */

object Stark {

    fun setApplication(context: Context) {
    }

    /**
     * Get the sensitivity threshold of shake detection in G's. Default is 3
     *
     * @return sensitivity Sensitivity of shake detection in G's. Lower is easier to activate.
     */
    fun getShakeGestureSensitivity(): Float {
        return 0f
    }

    /**
     * Set the sensitivity threshold of shake detection in G's. Default is 3
     *
     * @param sensitivity Sensitivity of shake detection in G's. Lower is easier to activate.
     */
    fun setShakeGestureSensitivity(sensitivity: Float) {
    }

    /**
     * Manually trigger the Stark menu embedded in the current foreground [Activity]
     *
     * @return true if the menu was opened, otherwise false
     */
    fun open(): Boolean {
        return false
    }

    /**
     * Manually trigger the Stark menu embedded in the given [Activity] to open.
     *
     * @param activity the [Activity] containing the menu to open.
     * @return true if the menu was opened, otherwise false
     */
    fun open(activity: Activity?): Boolean {
        return false
    }

    /**
     * Manually trigger closing the Stark menu embedded in the current foreground [Activity]
     *
     * @return true if the menu was closed, otherwise false
     */
    fun close(): Boolean {
        return false
    }

    /**
     * Manually trigger the Stark menu embedded in the given [Activity] to close.
     *
     * @param activity the [Activity] containing the menu to close.
     * @return true if the menu was closed, otherwise false
     */
    fun close(activity: Activity?): Boolean {
        return false
    }

    /**
     * Get top activity if possible
     */
    fun topActivity(): Activity? {
        return null
    }

    fun getApplication(): Application? {
        return null
    }

    /**
     * Hook to manually register a plugin source.
     * This API does not update the Stark menu retroactively,
     * so clients should call this as early as possible.
     *
     * NOTE: For most users, the default [java.util.ServiceLoader] implementation will suffice.
     *
     * @param pluginSource the [PluginSource] invoked in place of the default implementation.
     */
    fun setPlugins(pluginSource: Any?) {
    }

    /**
     * Get a plugin source.
     * Clients can wrap and delegate the [PluginSource].
     */
    fun getPluginSource(): Any? {
        return null
    }
}