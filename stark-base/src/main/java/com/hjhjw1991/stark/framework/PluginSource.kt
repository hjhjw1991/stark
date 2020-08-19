package com.hjhjw1991.stark.framework

import com.hjhjw1991.stark.plugin.Plugin

/**
 * @author huangjun.barney
 * @since 2020-05-12
 */

interface PluginSource {
    fun getPlugins(): Set<Plugin>
}