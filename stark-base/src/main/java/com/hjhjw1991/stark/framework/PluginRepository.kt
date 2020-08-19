package com.hjhjw1991.stark.framework

import com.hjhjw1991.stark.plugin.Plugin
import javax.inject.Inject
import javax.inject.Provider
import dagger.Lazy

/**
 * @author huangjun.barney
 * @since 2020-05-14
 */

@AppScope
class PluginRepository @Inject constructor(private val pluginSource: PluginSource) {
    private val plugins: Lazy<Plugins?> =
        Cached(PluginsProvider())
    fun getPlugins(): Plugins? {
        return plugins.get()
    }

    private inner class PluginsProvider : Provider<Plugins?> {
        override fun get(): Plugins {
            val plugins: Set<Plugin> = pluginSource.getPlugins()
            val compatiblePlugins: MutableSet<Plugin> = HashSet()
            for (plugin in plugins) {
                if (plugin.deviceMeetsMinimumApiRequirement()) {
                    compatiblePlugins.add(plugin)
                }
            }
            return Plugins(compatiblePlugins)
        }
    }

}