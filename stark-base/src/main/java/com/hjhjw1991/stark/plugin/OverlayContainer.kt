package com.hjhjw1991.stark.plugin

import android.support.annotation.LayoutRes
import android.view.View

/**
 * 浮层容器
 * @author huangjun.barney
 * @since 2020-05-15
 */

interface OverlayContainer {
    var overlayView: View?
    fun setOverlayView(@LayoutRes layout: Int)

    fun removeOverlayView(): Boolean
    fun addOnOverlayViewChangedListener(listener: OnOverlayViewChangedListener)
    fun removeOnOverlayViewChangedListener(listener: OnOverlayViewChangedListener): Boolean
}