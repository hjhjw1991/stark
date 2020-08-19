package com.hjhjw1991.stark.framework

import android.content.Context
import android.graphics.drawable.Drawable
import android.hardware.Sensor
import android.hardware.SensorManager
import android.support.annotation.LayoutRes
import android.support.v4.content.ContextCompat
import android.support.v4.view.MarginLayoutParamsCompat
import android.support.v4.view.ViewCompat
import android.support.v4.view.ViewPropertyAnimatorListenerAdapter
import android.support.v4.view.animation.FastOutLinearInInterpolator
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.view.animation.Interpolator
import com.hjhjw1991.stark.scaffold.debug.HJ
import com.hjhjw1991.stark.stark.R
import com.hjhjw1991.stark.plugin.*
import com.hjhjw1991.stark.util.SystemUtils

/**
 * 插件菜单管理器, 持有插件的入口view
 */
class StarkMenuController(private val container: ViewGroup) : StarkMenu,
    OverlayContainer {
    // 插件列表view
    private var pluginView: StarkPluginView? = null
    private val backgroundView: View = View(container.context)
    private val foregroundView: View
    private val onMenuStateChangedListeners: MutableList<OnMenuStateChangedListener> =
        ArrayList()
    private var menuState: MenuState =
        MenuState.CLOSE
    private val onOverlayViewChangedListeners: MutableList<OnOverlayViewChangedListener> =
        ArrayList()
    private val sensorManager: SensorManager?
    private val accelerometer: Sensor?
    private val shakeDetector: ShakeDetector
    private val screenWidth = SystemUtils.getScreenWidth(container.context)
    private var background: Drawable? = null
    fun setPluginView(view: StarkPluginView?) {
        pluginView?.setOnKeyListener(null)
        pluginView = view
        pluginView?.run {
            isFocusable = true
            isFocusableInTouchMode = true
            setOnClickListener {
                object : View.OnKeyListener {
                    override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                        if (keyCode == KeyEvent.KEYCODE_BACK) {
                            if (event.action == KeyEvent.ACTION_UP) {
                                collapse()
                            }
                            return true
                        }
                        return false
                    }
                }
            }
        }
    }

    override fun expand(): Boolean {
        if (menuState != MenuState.CLOSE) {
            return false
        }
        setMenuState(MenuState.OPENING)
        return expandAsFloatView()
    }

    private fun expandAsFloatView() : Boolean {
        // 进入这个方法的前提是stark开启, 所以不用判断
        // pluginView默认在左上角, 计算它到浮窗的位移偏移, 显示到浮窗旁边
        // 如果没有浮窗, 则位置始终在[10, 10]
        val floatingView = StarkFloatingViewManager.get()?.getView()
        val offsetY = floatingView?.y?: 10f
        val offsetX = floatingView
            ?.let { if (it.x < screenWidth/2) { it.width.toFloat() } else { screenWidth - it.context.resources.getDimension(R.dimen.stark_plugin_dock_width) - it.width } }
            ?: 10f
        container.addView(
            backgroundView, 0,
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
        val pluginParams =
                if (Stark.getPluginSource()?.getPlugins().isNullOrEmpty()) {
                    MarginLayoutParams(
                            40,
                            40
                    )
                } else {
                    MarginLayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                }
        MarginLayoutParamsCompat.setMarginStart(pluginParams, 0)
        container.addView(
            foregroundView,
            ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        )
        container.addView(pluginView, pluginParams)
        // 如果设置了浮层, 将浮层挪开, 不挡住插件菜单
        overlayView?.let {
            ViewCompat.animate(it)
                .translationY(-offsetY)
                .translationZ(10f)
                .setInterpolator(EXPAND_COLLAPSE_INTERPOLATOR)
                .start()
        }
        ViewCompat.setElevation(pluginView as View, 12f)
        ViewCompat.animate(pluginView as View)
            .withLayer()
            .alpha(1.0f)
            .translationY(offsetY)
            .translationX(offsetX)
            .setInterpolator(EXPAND_COLLAPSE_INTERPOLATOR)
            .setListener(object : ViewPropertyAnimatorListenerAdapter() {
                override fun onAnimationEnd(view: View?) {
                    setMenuState(MenuState.OPEN)
                    pluginView?.requestFocus()
                }
            })
            .start()
        return true
    }

    override fun collapse(): Boolean {
        if (menuState != MenuState.OPEN) {
            return false
        }
        setMenuState(MenuState.CLOSING)
        return collapseAsFloatView()
    }

    private fun collapseAsFloatView() : Boolean {
        // 如果设置了浮层, 将浮层恢复
        overlayView?.let {
            ViewCompat.animate(it)
                .translationY(0f)
                .translationZ(0.0f)
                .setInterpolator(EXPAND_COLLAPSE_INTERPOLATOR)
                .start()
        }
        ViewCompat.animate(pluginView as View)
            .withLayer()
            .alpha(0.0f)
            .translationX(OFFSET_INVISIBLE)
            .translationY(0.0f)
            .setInterpolator(EXPAND_COLLAPSE_INTERPOLATOR)
            .setListener(object : ViewPropertyAnimatorListenerAdapter() {
                override fun onAnimationEnd(view: View?) {
                    ViewCompat.setBackground(contentView, background)
                    background = null
                    container.removeView(backgroundView)
                    container.removeView(pluginView)
                    container.removeView(foregroundView)
                    setMenuState(MenuState.CLOSE)
                }
            })
            .start()
        return true
    }

    fun onResume() {
        HJ.say("stark menu controller onResume")
        sensorManager?.registerListener(shakeDetector, accelerometer, SensorManager.SENSOR_DELAY_UI)
    }

    fun onPause() {
        sensorManager?.unregisterListener(shakeDetector)
    }

    fun setShakeGestureSensitivity(sensitivity: Float) {
        shakeDetector.setShakeGestureSensitivity(sensitivity)
    }

    val contentView: View
        get() {
            for (i in 0 until container.childCount) {
                val child: View = container.getChildAt(i)
                val id: Int = child.id
                if (id != R.id.stark_background && id != R.id.stark_plugins && id != R.id.stark_overlay) {
                    return child
                }
            }
            throw IllegalStateException("Missing content view")
        }

    override fun getMenuState(): MenuState {
        return menuState
    }

    fun setMenuState(menuState: MenuState) {
        if (this.menuState != menuState) {
            this.menuState = menuState
            for (listener in onMenuStateChangedListeners) {
                listener.onMenuStateChanged(menuState)
            }
        }
    }

    override fun addOnMenuStateChangedListener(listener: OnMenuStateChangedListener) {
        onMenuStateChangedListeners.add(listener)
    }

    override fun removeOnMenuStateChangedListener(listener: OnMenuStateChangedListener): Boolean {
        return onMenuStateChangedListeners.remove(listener)
    }

    override fun setOverlayView(@LayoutRes layout: Int) {
        val overlay: View = LayoutInflater.from(container.context)
            .inflate(layout, container, false)
        overlayView = overlay
    }

    // overlay always right above content
    override var overlayView: View?
        get() = container.findViewById(R.id.stark_overlay) // may be null if overlayView is not set
        set(view) {
            removeOverlayView()
            view?.id = R.id.stark_overlay
            // overlay always right above content
            val contentView: View = contentView
            container.addView(
                view,
                container.indexOfChild(contentView) + 1,
                ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
            notifyOverlayViewChanged(view)
        }

    override fun removeOverlayView(): Boolean {
        val overlay: View? = container.findViewById(R.id.stark_overlay)
        container.removeView(overlay)
        notifyOverlayViewChanged(null)
        return true
    }

    override fun addOnOverlayViewChangedListener(listener: OnOverlayViewChangedListener) {
        onOverlayViewChangedListeners.add(listener)
    }

    override fun removeOnOverlayViewChangedListener(listener: OnOverlayViewChangedListener): Boolean {
        return onOverlayViewChangedListeners.remove(listener)
    }

    private fun notifyOverlayViewChanged(view: View?) {
        for (listener in onOverlayViewChangedListeners) {
            listener.onOverlayViewChanged(view)
        }
    }

    companion object {
        const val OFFSET_INVISIBLE = -10000f
        private val EXPAND_COLLAPSE_INTERPOLATOR: Interpolator = FastOutLinearInInterpolator()

        private fun cloneDrawable(drawable: Drawable?): Drawable? {
            // Safely clone the drawable so the original one doesn't get messed up.
            if (drawable == null) {
                return null
            }
            val constantState = drawable.constantState
            return if (constantState != null) constantState.newDrawable() else drawable.mutate()
        }
    }

    init {
        backgroundView.id = R.id.stark_background
        backgroundView.setBackgroundColor(
            ContextCompat.getColor(
                container.context,
                R.color.stark_menu_background
            )
        )
        foregroundView = View(container.context)
        ViewCompat.setElevation(foregroundView, 10.0f)
        foregroundView.setOnClickListener {
            collapse()
        }
        sensorManager =
            container.context.getSystemService(Context.SENSOR_SERVICE) as? SensorManager
        accelerometer = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        shakeDetector = ShakeDetector()
        shakeDetector.setOnShakeListener(object :
            ShakeDetector.OnShakeListener {
            override fun onShake() {
                // 摇一摇收起菜单
                collapse()
            }
        })
    }
}
