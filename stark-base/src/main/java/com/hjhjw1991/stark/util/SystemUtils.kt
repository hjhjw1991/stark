package com.hjhjw1991.stark.util

import android.app.Application
import android.content.Context

/**
 * @author huangjun.barney
 * @since 2020-06-01
 */
object SystemUtils {
    @JvmStatic
    fun getStatusBarHeight(context: Context): Int {
        var result = 0
        val resourceId: Int =
            context.resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            result = context.resources.getDimensionPixelSize(resourceId)
        }
        return result
    }

    @JvmStatic
    fun getScreenWidth(context: Context): Int {
        var screenWith = -1
        try {
            screenWith = context.resources.displayMetrics.widthPixels
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return screenWith
    }

    @JvmStatic
    fun getScreenHeight(context: Context): Int {
        var screenHeight = -1
        try {
            screenHeight = context.resources.displayMetrics.heightPixels
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return screenHeight
    }

    /**
     * @param key 设置的properties属性key. 注意key长度不要超过32, 否则在Android O以下会抛异常
     */
    @JvmStatic
    fun getProp(key: String, def: String = ""): String {
        val value = Class.forName("android.os.SystemProperties")
            .getMethod("get", String::class.java)
            .invoke(null, key)
        return if (value == null) def else value as String
    }

    /**
     * @param key 设置的properties属性key. 注意key长度不要超过32, 否则在Android O以下会抛异常
     */
    @JvmStatic
    fun getProp(key: String, def: Boolean = false): Boolean {
        val value = getProp(key, def.toString())
        return value.toBoolean()
    }

    object ApplicationContext {
        private var INSTANCE: Application? = null

        @JvmStatic
        fun get(): Application? {
            return INSTANCE
        }

        init {
            var app: Application? = null
            try {
                app = Class.forName("android.app.AppGlobals")
                    .getMethod("getInitialApplication").invoke(null) as Application
                checkNotNull(app) { "Static initialization of Applications must be on main thread." }
            } catch (e: java.lang.Exception) {
                e.printStackTrace()
                try {
                    app = Class.forName("android.app.ActivityThread")
                        .getMethod("currentApplication").invoke(null) as Application
                } catch (ex: java.lang.Exception) {
                    e.printStackTrace()
                }
            } finally {
                INSTANCE = app
            }
        }
    }
}