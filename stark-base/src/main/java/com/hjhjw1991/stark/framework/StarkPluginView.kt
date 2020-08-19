package com.hjhjw1991.stark.framework

import android.content.Context
import android.support.annotation.AttrRes
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.hjhjw1991.stark.scaffold.debug.HJ
import com.hjhjw1991.stark.stark.BuildConfig
import com.hjhjw1991.stark.stark.R
import com.hjhjw1991.stark.plugin.PluginModule
import com.hjhjw1991.stark.util.StarkGlobalSp
import kotlinx.android.synthetic.main.stark_view_plugin.view.*
import java.util.*
import javax.inject.Inject

/**
 * @author huangjun.barney
 * @since 2020-05-14
 */

class StarkPluginView(context: Context, attrs: AttributeSet?, @AttrRes defStyleAttr: Int):
    FrameLayout(context, attrs, defStyleAttr) {

    @Inject lateinit var modules: Set<@JvmSuppressWildcards PluginModule>
    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)

    init {
        val component =
            ComponentProvider.get(
                context
            )
        component.inject(this)

        inflate(context, R.layout.stark_view_plugin, this)
        translationX = -10000f
        val versionText = version_text
        versionText.text = context.getString(R.string.stark_version_text, BuildConfig.VERSION_NAME)

        // 插件入口列表
        // fixme 暂时用ScrollView+LinearLayout展示, 考虑用RecycleView或ListView
        val pluginListContainer = plugin_list_container
        val pluginExtension =
            PluginExtensionImpl(component)
        fitsSystemWindows = true
        id = R.id.stark_plugins
        ViewCompat.setOnApplyWindowInsetsListener(this) { _
                                                          , insets ->
            insets.apply {
                setPadding(
                    systemWindowInsetLeft,
                    systemWindowInsetTop,
                    systemWindowInsetRight,
                    systemWindowInsetBottom
                )
            }
        }

        val comparator =
            AlphabeticalComparator(
                context
            )
        val sortedModules = TreeSet(comparator)
        sortedModules.addAll(modules)
        HJ.say("modules $sortedModules")
        if (StarkGlobalSp.isStarkEnabled()) {
            val inflaterContext =
                PluginExtensionContextWrapper(
                    context,
                    pluginExtension
                )
            val inflater = LayoutInflater.from(inflaterContext)
            for (module in sortedModules) {
                if (module.isValid()) {
                    module.createPluginView(inflater, pluginListContainer)?.let {
                        HJ.say("add a plugin view for plugin ${context.getString(module.getName())}")
                        pluginListContainer.addView(it)
                    }
                }
            }
        }
    }

    // 插件模块按名字以字典序排序
    class AlphabeticalComparator internal constructor(val context: Context): Comparator<PluginModule> {

        override fun compare(left: PluginModule, right: PluginModule): Int {
            return getName(left).compareTo(getName(right))
        }

        // 使用默认名字的插件会按类名排序, 注意不要同名, 否则只有一个会显示
        // fixme 应该是treeset和这个comparator一起用的bug
        fun getName(pluginModule: PluginModule): String {
            val resName = pluginModule.getName()
            return if (resName == R.string.stark_module_name) pluginModule.javaClass.simpleName
            else context.getString(resName)
        }
    }
}