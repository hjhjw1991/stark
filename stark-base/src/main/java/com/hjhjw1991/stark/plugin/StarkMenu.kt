package com.hjhjw1991.stark.plugin

/**
 * 插件菜单接口
 * @author huangjun.barney
 */
interface StarkMenu {

    fun getMenuState(): MenuState?

    fun addOnMenuStateChangedListener(listener: OnMenuStateChangedListener)

    fun removeOnMenuStateChangedListener(listener: OnMenuStateChangedListener): Boolean

    /**
     * Request to open the menu.
     *
     * @return true if the menu was opened
     */
    fun expand(): Boolean

    /**
     * Request to close the menu.
     *
     * @return true if the menu was closed
     */
    fun collapse(): Boolean
}
