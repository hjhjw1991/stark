package com.hjhjw1991.stark.framework

import android.app.Application
import android.support.annotation.MainThread
import com.hjhjw1991.stark.plugin.ApplicationExtension
import javax.inject.Inject


class ApplicationInstaller @Inject constructor(
    private val pluginRepository: PluginRepository,
    private val application: Application,
    private val applicationExtension: ApplicationExtension
) {
    private var applicationCreated = false

    @MainThread
    fun installIfNeeded() {
        if (!applicationCreated) {
            val plugins: Plugins? = pluginRepository.getPlugins()
            plugins?.let {
                for (plugin in it.get()) {
                    plugin.create(application, applicationExtension)
                }
            }
            applicationCreated = true
        }
    }

}
