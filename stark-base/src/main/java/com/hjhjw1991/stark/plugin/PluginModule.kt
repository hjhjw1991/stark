package com.hjhjw1991.stark.plugin

import android.content.Context
import android.support.annotation.StringRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hjhjw1991.stark.stark.R

/**
 * 插件的控制模块
 */
abstract class PluginModule {
    private var extension: PluginExtension? = null
    private var context: Context? = null

    fun create(extension: PluginExtension?, context: Context?) {
        this.extension = extension
        this.context = context
        onCreate()
    }

    /**
     * The name of the plugin module. This should be the same name as displayed by the returned plugin view.
     *
     * @return the name of the plugin module
     */
    @StringRes
    open fun getName(): Int {
        return R.string.stark_module_name
    }

    protected open fun onCreate() {}

    /**
     * 创建插件入口view
     * create entry view for plugin
     */
    abstract fun createPluginView(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): View?

    /**
     * 插件是否可用
     * 若插件不可用, 则不展示插件入口
     * NOTE: [com.hjhjw1991.stark.stark.framework.Stark.topActivity] may be null when initializing
     * @return false if plugin module is invalid. default is true
     */
    open fun isValid() : Boolean { return true }

    fun destroy() {
        this.extension = null
        context = null
        onDestroy()
    }

    protected open fun onDestroy() {}

    fun getExtension(): PluginExtension? {
        return this.extension
    }

    fun getContext(): Context? {
        return context
    }
}
