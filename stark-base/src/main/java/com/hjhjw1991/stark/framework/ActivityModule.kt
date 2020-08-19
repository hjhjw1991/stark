package com.hjhjw1991.stark.framework

import android.app.Activity
import android.content.res.Resources
import android.util.DisplayMetrics
import android.view.LayoutInflater
import com.hjhjw1991.stark.plugin.PluginModule
import dagger.Module
import dagger.Provides

@Module
object ActivityModule {
    @JvmStatic
    @Provides
    fun provideLayoutInflater(activity: Activity?): LayoutInflater {
        return LayoutInflater.from(activity)
    }

    @JvmStatic
    @Provides
    fun provideResources(activity: Activity): Resources {
        return activity.resources
    }

    @JvmStatic
    @Provides
    fun provideDisplayMetrics(resources: Resources): DisplayMetrics {
        return resources.displayMetrics
    }

    @JvmStatic
    @Provides
    @ActivityScope
    fun modules(pluginRepository: PluginRepository): Set<PluginModule> {
        return pluginRepository.getPlugins()!!.createModules()
    }
}
