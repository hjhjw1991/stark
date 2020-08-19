package com.hjhjw1991.stark.framework

import android.app.Activity

/**
 * @author huangjun.barney
 * @since 2020-05-14
 */

interface PublicControl {
    /**
     * 获取当前在前台的Activity. 可能为空
     */
    fun foregroundActivity(): Activity?
    fun open(): Boolean
    fun open(activity: Activity?): Boolean
    fun close(): Boolean
    fun close(activity: Activity?): Boolean
    fun setShakeGestureSensitivity(sensitivity: Float)
    fun getShakeGestureSensitivity(): Float
    fun setPluginSource(pluginSource: PluginSource?)
    fun getPluginSource(): PluginSource?
}