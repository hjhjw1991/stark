package com.hjhjw1991.stark.framework

import android.content.Context
import android.content.res.Configuration
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.MotionEvent
import android.widget.FrameLayout
import com.hjhjw1991.stark.util.SystemUtils


/**
 * @author huangjun.barney
 * @since 2020-06-01
 */
open class StarkFloatingView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    FrameLayout(context, attrs, defStyleAttr) {
    private var mOriginalRawX = 0f
    private var mOriginalRawY = 0f
    private var mOriginalX = 0f
    private var mOriginalY = 0f
    private var mStarkMagnetViewListener: StarkMagnetViewListener? = null
    private var mLastTouchDownTime: Long = 0
    protected var mMoveAnimator: MoveAnimator? = null
    protected var mScreenWidth = 0
    private var mScreenHeight = 0
    private var mStatusBarHeight = 0
    private var isNearestLeft = true
    fun setStarkMagnetViewListener(StarkMagnetViewListener: StarkMagnetViewListener?) {
        mStarkMagnetViewListener = StarkMagnetViewListener
    }

    private fun init() {
        mMoveAnimator = MoveAnimator()
        mStatusBarHeight = SystemUtils.getStatusBarHeight(context)
        isClickable = true
        updateSize()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event == null) {
            return false
        }
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                changeOriginalTouchParams(event)
                updateSize()
                mMoveAnimator?.stop()
            }
            MotionEvent.ACTION_MOVE -> updateViewPosition(event)
            MotionEvent.ACTION_UP -> {
                moveToEdge()
                if (isOnClickEvent()) {
                    dealClickEvent()
                }
            }
        }
        return true
    }

    protected fun dealClickEvent() {
        mStarkMagnetViewListener?.onClick(this)
    }

    protected fun isOnClickEvent(): Boolean {
        return System.currentTimeMillis() - mLastTouchDownTime < TOUCH_TIME_THRESHOLD
    }

    private fun updateViewPosition(event: MotionEvent) {
        x = mOriginalX + event.rawX - mOriginalRawX
        // 限制不可超出屏幕高度
        var desY = mOriginalY + event.rawY - mOriginalRawY
        if (desY < mStatusBarHeight) {
            desY = mStatusBarHeight.toFloat()
        }
        if (desY > mScreenHeight - height) {
            desY = mScreenHeight - height.toFloat()
        }
        y = desY
    }

    private fun changeOriginalTouchParams(event: MotionEvent) {
        mOriginalX = x
        mOriginalY = y
        mOriginalRawX = event.rawX
        mOriginalRawY = event.rawY
        mLastTouchDownTime = System.currentTimeMillis()
    }

    protected fun updateSize() {
        mScreenWidth = SystemUtils.getScreenWidth(context) - this.width
        mScreenHeight = SystemUtils.getScreenHeight(context)
    }

    @JvmOverloads
    fun moveToEdge(isLeft: Boolean = isNearestLeft()) {
        val moveDistance =
            (if (isLeft) MARGIN_EDGE else mScreenWidth - MARGIN_EDGE).toFloat()
        mMoveAnimator!!.start(moveDistance, y)
    }

    protected fun isNearestLeft(): Boolean {
        val middle = mScreenWidth / 2
        isNearestLeft = x < middle
        return isNearestLeft
    }

    fun onRemove() {
        mStarkMagnetViewListener?.onRemove(this)
    }

    protected inner class MoveAnimator : Runnable {
        private val handler = Handler(Looper.getMainLooper())
        private var destinationX = 0f
        private var destinationY = 0f
        private var startingTime: Long = 0
        fun start(x: Float, y: Float) {
            destinationX = x
            destinationY = y
            startingTime = System.currentTimeMillis()
            handler.post(this)
        }

        override fun run() {
            if (rootView == null || rootView.parent == null) {
                return
            }
            val progress =
                Math.min(1f, (System.currentTimeMillis() - startingTime) / 400f)
            val deltaX = (destinationX - x) * progress
            val deltaY = (destinationY - y) * progress
            move(deltaX, deltaY)
            if (progress < 1) {
                handler.post(this)
            }
        }

        fun stop() {
            handler.removeCallbacks(this)
        }
    }

    private fun move(deltaX: Float, deltaY: Float) {
        x = x + deltaX
        y = y + deltaY
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        super.onConfigurationChanged(newConfig)
        updateSize()
        moveToEdge(isNearestLeft)
    }

    companion object {
        const val MARGIN_EDGE = 13
        private const val TOUCH_TIME_THRESHOLD = 150
    }

    init {
        init()
    }
}