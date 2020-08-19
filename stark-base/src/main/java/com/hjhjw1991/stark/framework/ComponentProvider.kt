package com.hjhjw1991.stark.framework

import android.annotation.SuppressLint
import android.content.Context

class ComponentProvider {

    companion object {
        const val COMPONENT = "component"

        @SuppressLint("WrongConstant")
        fun get(context: Context): CoreComponent {
            return context.getSystemService(COMPONENT) as CoreComponent
        }
    }

    private fun ComponentProvider() {
        throw AssertionError("No instances.")
    }
}
