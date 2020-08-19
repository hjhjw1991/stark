package com.hjhjw1991.stark.framework

import android.app.Activity
import android.content.Context
import com.hjhjw1991.stark.plugin.PluginExtension
import com.hjhjw1991.stark.plugin.PluginModule
import javax.inject.Inject

@AppScope
class CoreComponentContainer @Inject constructor() {
    // array map is more suitable
    private val components: HashMap<Activity, CoreComponent> = HashMap()
    private val pluginModules: HashMap<Activity, Set<PluginModule>> =
        HashMap()
    var pluginSource: PluginSource =
        ServiceLoaderPluginSource()

    fun getComponent(activity: Activity?): CoreComponent? {
        return components[activity]
    }

    fun removeComponent(activity: Activity?) {
        components.remove(activity)
        val removedPluginModules: Set<PluginModule>? =
            pluginModules.remove(activity)
        removedPluginModules?.forEach { module ->
            module.destroy()
        }
    }

    fun putComponent(activity: Activity, component: CoreComponent) {
        components[activity] = component
        component.pluginModules.let {
            this@CoreComponentContainer.pluginModules[activity] = it
            val pluginExtension: PluginExtension =
                PluginExtensionImpl(component)
            val context: Context =
                PluginExtensionContextWrapper(
                    activity,
                    pluginExtension
                )
            for (module in it) {
                module.create(pluginExtension, context)
            }
        }
    }

    val activities: MutableSet<Activity>?
        get() = components.keys

}
