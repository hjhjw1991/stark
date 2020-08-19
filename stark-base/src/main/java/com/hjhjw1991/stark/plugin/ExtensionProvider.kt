package com.hjhjw1991.stark.plugin

import android.annotation.SuppressLint
import android.content.Context

class ExtensionProvider private constructor() {
    companion object {
        const val NAME = "extension"

        @SuppressLint("WrongConstant")
        operator fun get(context: Context): PluginExtension {
            return context.getSystemService(NAME) as PluginExtension
        }
    }

    init {
        throw AssertionError("No instances.")
    }
}
