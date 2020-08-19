package com.hjhjw1991.stark.plugin

import android.app.Activity
import android.view.ViewGroup


interface PluginExtension {
    /**
     * 获取当前绑定的Activity. 在onCreate之后绑定
     */
    fun getActivity(): Activity

    /**
     * 获取当前绑定的插件列表的rootView
     */
    fun getContentRoot(): ViewGroup

    fun getOverlayContainer(): OverlayContainer

    fun getActivityResults(): ActivityResults

    fun getAttributeTranslator(): AttributeTranslator?

    fun getMeasurementHelper(): MeasurementHelper?

    val starkMenu: StarkMenu
}
