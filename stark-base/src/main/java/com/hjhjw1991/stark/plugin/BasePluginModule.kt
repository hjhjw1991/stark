package com.hjhjw1991.stark.plugin

import android.support.annotation.DrawableRes
import android.support.annotation.IdRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hjhjw1991.stark.stark.R
import kotlinx.android.synthetic.main.stark_base_plugin_entry.view.*

/**
 * base plugin module
 * @author huangjun.barney
 * @since 2020/6/8
 */

abstract class BasePluginModule : PluginModule() {
    override fun createPluginView(layoutInflater: LayoutInflater, parent: ViewGroup): View? {
        val pluginView = layoutInflater.inflate(R.layout.stark_base_plugin_entry, parent, false)
        pluginView.apply {
            stark_plugin_logo.setImageResource(getPluginLogo())
            stark_plugin_name.text = getPluginName()
//            stark_plugin_description.text = getPluginDescription()
            setOnClickListener {
                onClickPluginView(it)
            }
        }
        onCreatePluginView(pluginView)
        return pluginView
    }

    abstract fun onCreatePluginView(view: View?)

    abstract fun onClickPluginView(view: View?)

    /**
     * resource id for plugin logo
     */
    @IdRes
    fun getPluginLogoId() : Int {
        return R.id.stark_plugin_logo
    }

    /**
     * resource id for plugin name
     */
    @IdRes
    fun getPluginNameId() : Int {
        return R.id.stark_plugin_name
    }

    /**
     * plugin entry logo on plugin desk
     */
    @DrawableRes
    abstract fun getPluginLogo() : Int

    /**
     * plugin name on plugin desk
     */
    abstract fun getPluginName() : String

    /**
     * plugin description on plugin desk
     */
    abstract fun getPluginDescription() : String
}