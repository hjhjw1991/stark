package com.hjhjw1991.stark.util

import android.content.Context
import android.content.SharedPreferences
import android.support.annotation.Nullable

object StarkGlobalSp {
    /**
     * 控制是否开启插件能力的开关
     */
    const val STARK_PROP_ENABLE = "debug.stark.enable"
    /**
     * 控制是否开启通知栏服务的开关
     */
    const val STARK_PROP_ENABLE_SERVICE = "debug.stark.enable_service"

    /**
     * 控制是否开启悬浮窗的开关
     */
    const val STARK_PROP_ENABLE_FLOATING_VIEW = "debug.stark.enable_fv"

    /**
     * Stark全局设置SharePrefs文件名
     */
    const val STARK_GLOBAL_PREFS = "stark_debug_settings"

    private val sp: SharedPreferences by lazy {
        SystemUtils.ApplicationContext.get()
        SystemUtils.ApplicationContext.get()!!.getSharedPreferences(STARK_GLOBAL_PREFS, Context.MODE_PRIVATE)
    }

    /**
     * stark能力是否开启
     * 开关优先级: 外部SharePreference设置 > 手机SystemProperties设置
     * 仅在外部没有设置时, 才读取手机SystemProperties设置
     */
    @JvmStatic
    fun isStarkEnabled() : Boolean {
        if (sp.contains(STARK_PROP_ENABLE)) {
            return sp.getBoolean(STARK_PROP_ENABLE, false)
        }
        return SystemUtils.getProp(STARK_PROP_ENABLE, false)
    }
    /**
     * stark悬浮窗是否开启
     * 开关优先级: 外部SharePreference设置 > 手机SystemProperties设置
     * 仅在外部没有设置时, 才读取手机SystemProperties设置
     */
    @JvmStatic
    fun isStarkFloatingViewEnabled() : Boolean {
        if (sp.contains(STARK_PROP_ENABLE_FLOATING_VIEW)) {
            return isStarkEnabled() && sp.getBoolean(STARK_PROP_ENABLE_FLOATING_VIEW, false)
        }
        return isStarkEnabled() && SystemUtils.getProp(STARK_PROP_ENABLE_FLOATING_VIEW, false)
    }
    /**
     * stark服务是否开启
     * 开关优先级: 外部SharePreference设置 > 手机SystemProperties设置
     * 仅在外部没有设置时, 才读取手机SystemProperties设置
     */
    @JvmStatic
    fun isStarkServiceEnabled() : Boolean {
        if (sp.contains(STARK_PROP_ENABLE_SERVICE)) {
            return isStarkEnabled() && sp.getBoolean(STARK_PROP_ENABLE_SERVICE, false)
        }
        return isStarkEnabled() && SystemUtils.getProp(STARK_PROP_ENABLE_SERVICE, false)
    }

    @JvmStatic
    fun getString(key: String, @Nullable defValue: String?): String? {
        return sp.getString(key, defValue)
    }

    @JvmStatic
    fun getInt(key: String, defValue: Int): Int {
        return sp.getInt(key, defValue)
    }

    @JvmStatic
    fun getLong(key: String, defValue: Long): Long {
        return sp.getLong(key, defValue)
    }

    @JvmStatic
    fun getFloat(key: String, defValue: Float): Float {
        return sp.getFloat(key, defValue)
    }

    @JvmStatic
    fun getBoolean(key: String, defValue: Boolean): Boolean {
        return sp.getBoolean(key, defValue)
    }

    @JvmStatic
    fun putString(key: String, @Nullable value: String?) {
        sp.edit().putString(key, value).apply()
    }

    @JvmStatic
    fun putInt(key: String, value: Int) {
        sp.edit().putInt(key, value).apply()
    }

    @JvmStatic
    fun putLong(key: String, value: Long) {
        sp.edit().putLong(key, value).apply()
    }

    @JvmStatic
    fun putFloat(key: String, value: Float) {
        sp.edit().putFloat(key, value).apply()
    }

    @JvmStatic
    fun putBoolean(key: String, value: Boolean) {
        sp.edit().putBoolean(key, value).apply()
    }
}