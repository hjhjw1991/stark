package com.hjhjw1991.stark.framework

import android.app.Activity
import com.hjhjw1991.stark.plugin.ForegroundManager
import javax.inject.Inject


@AppScope
class PublicControlImpl @Inject constructor(
    private val container: CoreComponentContainer,
    private val foregroundManager: ForegroundManager
) :
    PublicControl {
    private var sensitivity = 3.0f
    override fun getShakeGestureSensitivity(): Float {
        return sensitivity
    }

    override fun setShakeGestureSensitivity(sensitivity: Float) {
        this.sensitivity = sensitivity
        for (activity in container.activities!!) {
            val component = container.getComponent(activity)
            component?.menuController?.setShakeGestureSensitivity(sensitivity)
        }
    }

    override fun foregroundActivity(): Activity? {
        return foregroundManager.foregroundActivity
    }

    override fun open(): Boolean {
        return foregroundManager.foregroundActivity?.let { open(it) } ?: false
    }

    override fun open(activity: Activity?): Boolean {
        val component = container.getComponent(activity) ?: return false
        return component.menuController.expand()
    }

    override fun close(): Boolean {
        return foregroundManager.foregroundActivity?.let { close(it) } ?: false
    }

    override fun close(activity: Activity?): Boolean {
        val component = container.getComponent(activity) ?: return false
        return component.menuController.collapse()
    }

    override fun setPluginSource(pluginSource: PluginSource?) {
        pluginSource?.let {
            container.pluginSource = it
        }
    }

    override fun getPluginSource(): PluginSource? {
        return container.pluginSource
    }

}
