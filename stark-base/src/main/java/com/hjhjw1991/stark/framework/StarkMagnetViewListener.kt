package com.hjhjw1991.stark.framework

import android.app.Activity
import android.support.annotation.DrawableRes
import android.support.annotation.LayoutRes
import android.view.ViewGroup
import android.widget.FrameLayout


/**
 * @author huangjun.barney
 * @since 2020-06-01
 */
interface StarkMagnetViewListener {
    fun onRemove(magnetView: StarkFloatingView)
    fun onClick(magnetView: StarkFloatingView)
}

interface IFloatingView {
    fun remove(): IFloatingView?
    fun add(): IFloatingView?

    /**
     * 附着悬浮窗到指定activity
     */
    fun attach(activity: Activity?): IFloatingView?
    fun attach(container: FrameLayout?): IFloatingView?

    /**
     * 从activity上解除悬浮窗
     */
    fun detach(activity: Activity?): IFloatingView?
    fun detach(container: FrameLayout?): IFloatingView?
    fun getView(): StarkFloatingView?

    /**
     * 悬浮窗icon
     */
    fun icon(@DrawableRes resId: Int): IFloatingView?
    fun customView(viewGroup: StarkFloatingView?): IFloatingView?
    fun customView(@LayoutRes resource: Int): IFloatingView?
    fun layoutParams(params: ViewGroup.LayoutParams?): IFloatingView?
    fun listener(magnetViewListener: StarkMagnetViewListener): IFloatingView?
}