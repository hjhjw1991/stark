package com.hjhjw1991.stark.framework

import android.content.Context
import android.content.ContextWrapper
import android.view.LayoutInflater
import com.hjhjw1991.stark.plugin.ExtensionProvider
import com.hjhjw1991.stark.plugin.PluginExtension


class PluginExtensionContextWrapper(base: Context, val extension: PluginExtension) :
    ContextWrapper(base) {

    private var inflater: LayoutInflater? = null
    override fun getSystemService(name: String): Any {
        if (Context.LAYOUT_INFLATER_SERVICE == name) {
            if (inflater == null) {
                inflater = LayoutInflater.from(baseContext).cloneInContext(this)
            }
            return inflater!!
        }
        return if (ExtensionProvider.NAME == name) {
            extension
        } else super.getSystemService(name)
    }
}