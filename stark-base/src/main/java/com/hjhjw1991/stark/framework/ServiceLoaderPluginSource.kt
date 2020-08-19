package com.hjhjw1991.stark.framework

import com.hjhjw1991.stark.plugin.Plugin
import java.util.*
import kotlin.collections.HashSet

/**
 * load all plugins with [java.util.ServiceLoader]
 */
internal class ServiceLoaderPluginSource :
    PluginSource {
    override fun getPlugins(): Set<Plugin> {
        val plugins: MutableSet<Plugin> =
            HashSet()
        val loader: ServiceLoader<Plugin> = ServiceLoader.load(
            Plugin::class.java, javaClass.classLoader
        )
        for (plugin in loader) {
            plugins.add(plugin)
        }
        return plugins
    }
}