package com.hjhjw1991.stark.plugin

import android.graphics.Rect
import android.support.annotation.MainThread
import android.support.annotation.Px
import android.view.View


/**
 * 测量工具类
 * @author huangjun.barney
 * @since 2020-05-15
 */
@MainThread
interface MeasurementHelper {
    @MainThread
    fun getParentRelativeRect(view: View, rect: Rect)

    @MainThread
    fun getRelativeLeft(view: View): Int

    @MainThread
    fun getRelativeTop(view: View): Int

    @MainThread
    fun getRelativeRight(view: View): Int

    @MainThread
    fun getRelativeBottom(view: View): Int

    @MainThread
    fun getScreenLocation(view: View, rect: Rect)

    /**
     * Returns the view's coordinates relative to the content root
     * ([PluginExtension.getContentRoot]).
     */
    @MainThread
    fun getContentRootLocation(view: View, rect: Rect)

    @Px
    fun toPx(dp: Int): Int
    fun toDp(@Px px: Int): Int
    fun toSp(px: Float): Int
}