package com.hjhjw1991.stark.plugin.external.scalpel

import android.view.View
import android.view.ViewGroup
import com.hjhjw1991.stark.framework.Stark
import com.hjhjw1991.stark.plugin.BasePluginModule
import com.hjhjw1991.stark.plugin.Plugin
import com.hjhjw1991.stark.plugin.PluginModule
import com.hjhjw1991.stark.plugin.external.R
import com.google.auto.service.AutoService
import com.jakewharton.scalpel.ScalpelFrameLayout

/**
 * 使用开源库scalpel展示view层级
 * @author huangjun.barney
 * @since 2020/6/12
 */

@AutoService(Plugin::class)
class ScalpelPlugin : Plugin() {
    override fun createPluginModule(): PluginModule? {
        return object : BasePluginModule() {
            override fun onCreatePluginView(view: View?) {
                // empty is ok
            }

            // fixme this performs poor
            override fun onClickPluginView(view: View?) {
                Stark.close()
                val activity = Stark.topActivity()
                activity?.let {
                    val content = it.window.decorView
                        .findViewById<ViewGroup>(android.R.id.content)
                    val contentChild = content.getChildAt(0)
                    val floatingView = content.getChildAt(1)
                    if (contentChild != null) {
                        if (contentChild is ScalpelFrameLayout) {
                            content.removeAllViews()
                            val originContent: View =
                                contentChild.getChildAt(0)
                            contentChild.removeAllViews()
                            content.addView(originContent)
                        } else {
                            content.removeAllViews()
                            val frameLayout = ScalpelFrameLayout(getContext())
                            frameLayout.isLayerInteractionEnabled = true
                            frameLayout.setDrawIds(true)
                            frameLayout.addView(contentChild)
                            content.addView(frameLayout)
                        }
                    }

                    if (floatingView != null) {
                        content.addView(floatingView)
                    }
                }
            }

            override fun getPluginLogo(): Int {
                return R.drawable.stark_plugin_external_surgery
            }

            override fun getName(): Int {
                return R.string.stark_plugin_scalpel
            }

            override fun getPluginName(): String {
                return Stark.getApplication()?.getString(R.string.stark_plugin_scalpel)?:"手术刀"
            }

            override fun getPluginDescription(): String {
                return "展示当前Activity的布局"
            }

        }
    }
}