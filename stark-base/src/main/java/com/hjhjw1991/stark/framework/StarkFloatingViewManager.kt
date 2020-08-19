package com.hjhjw1991.stark.framework

import android.app.Activity
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.support.annotation.DrawableRes
import android.support.annotation.LayoutRes
import android.support.v4.view.ViewCompat
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.RelativeLayout
import com.hjhjw1991.stark.scaffold.debug.HJ
import com.hjhjw1991.stark.stark.R
import com.hjhjw1991.stark.util.SystemUtils
import com.hjhjw1991.stark.util.ViewUtil
import java.lang.ref.WeakReference


/**
 * 悬浮窗管理类. 单例
 * @author huangjun.barney
 * @since 2020-06-01
 */

class StarkFloatingViewManager private constructor() :
    IFloatingView {
    private var mStarkFloatingView: StarkFloatingView? = null
    private var mContainer: WeakReference<FrameLayout>? = null

    @LayoutRes
    private var mLayoutId: Int = R.layout.stark_floating_view

    @DrawableRes
    private var mIconRes: Int = R.drawable.stark_logo
    private var mLayoutParams: ViewGroup.LayoutParams? = getParams()

    override fun remove(): IFloatingView {
        Handler(Looper.getMainLooper()).post {
            mStarkFloatingView?.takeIf { ViewCompat.isAttachedToWindow(it as View) }
                ?.let {
                    getContainer()?.removeView(it)
                    it.onRemove()
                }
            mStarkFloatingView = null
        }
        return this
    }

    private fun ensureFloatingView() {
        synchronized(this) {
            if (mStarkFloatingView != null) {
                return
            }
            val defaultFloatingView =
                DefaultFloatingView(
                    SystemUtils.ApplicationContext.get() as Context,
                    mLayoutId
                )
            mStarkFloatingView = defaultFloatingView
            defaultFloatingView.layoutParams = mLayoutParams
            defaultFloatingView.setIconImage(mIconRes)
            addViewToWindow(defaultFloatingView)
        }
    }

    override fun add(): IFloatingView {
        ensureFloatingView()
        return this
    }

    override fun attach(activity: Activity?): IFloatingView {
        attach(getActivityRoot(activity))
        return this
    }

    override fun attach(container: FrameLayout?): IFloatingView {
        if (mStarkFloatingView == null) {
            mContainer = WeakReference(container!!)
            return this
        }
        if (mStarkFloatingView?.parent === container) {
            return this
        }
        if (mStarkFloatingView?.parent === getContainer()) {
            getContainer()?.removeView(mStarkFloatingView)
        }
        mContainer = WeakReference(container!!)
        container.addView(mStarkFloatingView)
        return this
    }

    override fun detach(activity: Activity?): IFloatingView {
        detach(getActivityRoot(activity))
        return this
    }

    override fun detach(container: FrameLayout?): IFloatingView {
        mStarkFloatingView?.takeIf { ViewCompat.isAttachedToWindow(it) }
            ?.let { container?.removeView(it) }
        if (getContainer() === container) {
            mContainer = null
        }
        return this
    }

    override fun getView(): StarkFloatingView? {
        return mStarkFloatingView
    }

    override fun icon(@DrawableRes resId: Int): IFloatingView {
        mIconRes = resId
        return this
    }

    override fun customView(viewGroup: StarkFloatingView?): IFloatingView? {
        mStarkFloatingView = viewGroup
        return this
    }

    override fun customView(@LayoutRes resource: Int): IFloatingView? {
        mLayoutId = resource
        return this
    }

    override fun layoutParams(params: ViewGroup.LayoutParams?): IFloatingView? {
        mLayoutParams = params
        mStarkFloatingView?.layoutParams = params
        return this
    }

    override fun listener(magnetViewListener: StarkMagnetViewListener): IFloatingView {
        mStarkFloatingView?.setStarkMagnetViewListener(magnetViewListener)
        return this
    }

    private fun addViewToWindow(view: View) {
        getContainer()?.addView(view)
    }

    private fun getContainer(): FrameLayout? {
        return mContainer?.get()
    }

    private fun getParams(): FrameLayout.LayoutParams {
        val params = FrameLayout.LayoutParams(
            RelativeLayout.LayoutParams.WRAP_CONTENT,
            RelativeLayout.LayoutParams.WRAP_CONTENT
        )
        params.gravity = Gravity.BOTTOM or Gravity.START
        params.setMargins(13, params.topMargin, params.rightMargin, 500)
        return params
    }


    private fun getActivityRoot(activity: Activity?): FrameLayout? {
        return ViewUtil.getActivityRoot(activity)
    }

    companion object {
        @Volatile
        private var mInstance: StarkFloatingViewManager? = null
        fun get(): StarkFloatingViewManager? {
            if (mInstance == null) {
                synchronized(StarkFloatingViewManager::class.java) {
                    if (mInstance == null) {
                        mInstance =
                            StarkFloatingViewManager()
                    }
                }
            }
            return mInstance
        }
    }

    class StarkFloatingViewListener :
        StarkMagnetViewListener {
        var context: Activity? = null
        override fun onRemove(magnetView: StarkFloatingView) {
            HJ.say("on floating view removed")
        }

        override fun onClick(magnetView: StarkFloatingView) {
            HJ.say("on floating view clicked using activity $context")
            Stark.open(context)
        }
    }
}