package com.hjhjw1991.stark.framework

import android.app.Activity
import android.app.Application
import android.content.Context
import android.support.annotation.RestrictTo


/**
 * singleton stark
 * [open] to open debugger
 * @author huangjun.barney
 * @since 2020-05-12
 */

object Stark {
    private var application: Application? = null

    @RestrictTo(RestrictTo.Scope.LIBRARY_GROUP)
    fun setApplication(context: Context) {
        application = context.applicationContext as Application
    }

    /**
     * Get the sensitivity threshold of shake detection in G's. Default is 3
     *
     * @return sensitivity Sensitivity of shake detection in G's. Lower is easier to activate.
     */
    @JvmStatic
    fun getShakeGestureSensitivity(): Float {
        requireApplication()
        return AppComponent.getInstance(
            application
        ).publicControl
            .getShakeGestureSensitivity()
    }

    /**
     * Set the sensitivity threshold of shake detection in G's. Default is 3
     *
     * @param sensitivity Sensitivity of shake detection in G's. Lower is easier to activate.
     */
    @JvmStatic
    fun setShakeGestureSensitivity(sensitivity: Float) {
        requireApplication()
        AppComponent.getInstance(
            application
        ).publicControl
            .setShakeGestureSensitivity(sensitivity)
    }

    /**
     * Manually trigger the Stark menu embedded in the current foreground [Activity]
     *
     * @return true if the menu was opened, otherwise false
     */
    @JvmStatic
    fun open(): Boolean {
        requireApplication()
        return AppComponent.getInstance(
            application
        ).publicControl.open()
    }

    /**
     * Manually trigger the Stark menu embedded in the given [Activity] to open.
     *
     * @param activity the [Activity] containing the menu to open.
     * @return true if the menu was opened, otherwise false
     */
    @JvmStatic
    fun open(activity: Activity?): Boolean {
        requireApplication()
        return AppComponent.getInstance(
            application
        ).publicControl.open(activity)
    }

    /**
     * Manually trigger closing the Stark menu embedded in the current foreground [Activity]
     *
     * @return true if the menu was closed, otherwise false
     */
    @JvmStatic
    fun close(): Boolean {
        requireApplication()
        return AppComponent.getInstance(
            application
        ).publicControl.close()
    }

    /**
     * Manually trigger the Stark menu embedded in the given [Activity] to close.
     *
     * @param activity the [Activity] containing the menu to close.
     * @return true if the menu was closed, otherwise false
     */
    @JvmStatic
    fun close(activity: Activity?): Boolean {
        requireApplication()
        return AppComponent.getInstance(
            application
        ).publicControl.close(activity)
    }

    /**
     * Get top activity if possible
     */
    @JvmStatic
    fun topActivity(): Activity? {
        requireApplication()
        return AppComponent.getInstance(
            application
        ).publicControl.foregroundActivity()
    }

    @JvmStatic
    fun getApplication(): Application? {
        return application
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
    @JvmStatic
    fun setPlugins(pluginSource: PluginSource?) {
        requireApplication()
        AppComponent.getInstance(
            application
        ).publicControl
            .setPluginSource(pluginSource)
    }

    /**
     * Get a plugin source.
     * Clients can wrap and delegate the [PluginSource].
     */
    @JvmStatic
    fun getPluginSource(): PluginSource? {
        requireApplication()
        return AppComponent.getInstance(
            application
        ).publicControl.getPluginSource()
    }

    private fun requireApplication() {
        checkNotNull(application) { "Application has not been set." }
    }
}