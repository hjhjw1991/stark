package com.hjhjw1991.stark.framework

import android.content.Context
import android.view.ContextThemeWrapper
import com.hjhjw1991.stark.stark.R


class ComponentContextThemeWrapper(base: Context?, private val component: CoreComponent) :
    ContextThemeWrapper(base, R.style.Stark_Base) {
    override fun getSystemService(name: String): Any {
        return if (name == ComponentProvider.COMPONENT) {
            component
        } else super.getSystemService(name)
    }

}
