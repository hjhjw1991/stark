package com.hjhjw1991.stark.framework

import com.hjhjw1991.stark.plugin.Plugin
import com.hjhjw1991.stark.plugin.PluginModule
import java.util.*
import kotlin.collections.HashSet

/**
 * @author huangjun.barney
 * @since 2020-05-15
 */
class Plugins(private val plugins: Set<Plugin>) {
    fun get(): Set<Plugin> {
        return Collections.unmodifiableSet(plugins)
    }

    fun createModules(): Set<PluginModule> {
        val modules: MutableSet<PluginModule> = HashSet()
        for (plugin in plugins) {
            plugin.createPluginModule()?.let {
                modules.add(it)
            }
        }
        return modules
    }

}