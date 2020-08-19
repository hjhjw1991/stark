package com.hjhjw1991.stark.plugin

import android.content.Context
import android.os.Build

/**
 * 插件基类, 负责提供环境信息, 通过[ApplicationExtension]可提供应用切换前后台的回调
 * @author huangjun.barney
 * @since 2020-05-12
 */

abstract class Plugin {
    private var context: Context? = null
    private var extension: ApplicationExtension? = null
    fun create(context: Context,
        extension: ApplicationExtension
    ) {
        this.context = context
        this.extension = extension
        onApplicationCreated(context)
    }

    fun deviceMeetsMinimumApiRequirement(): Boolean {
        return Build.VERSION.SDK_INT >= minimumRequiredApi()
    }

    protected fun onApplicationCreated(context: Context) {}

    abstract fun createPluginModule(): PluginModule?

    fun getContext(): Context? {
        return context
    }

    protected fun getApplicationExtension(): ApplicationExtension? {
        return extension
    }

    /**
     * Define the minimum required API level for the plugin. The Stark minimum supported API is ICS_MR1 (15).
     *
     * @return minimum required api level
     */
    protected fun minimumRequiredApi(): Int {
        return Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1
    }
}