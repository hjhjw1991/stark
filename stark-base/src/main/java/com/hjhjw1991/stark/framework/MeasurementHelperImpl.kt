package com.hjhjw1991.stark.framework

import android.graphics.Rect
import android.support.annotation.Px
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import com.hjhjw1991.stark.plugin.MeasurementHelper
import javax.inject.Inject

/**
 * 测量工具类注入类
 */
class MeasurementHelperImpl @Inject constructor(
    private val displayMetrics: DisplayMetrics,
    private val container: ViewGroup
) :
    MeasurementHelper {
    override fun getParentRelativeRect(
        view: View,
        rect: Rect
    ) {
        rect.left = getRelativeLeft(view)
        rect.top = getRelativeTop(view)
        rect.right = getRelativeRight(view)
        rect.bottom = getRelativeBottom(view)
    }

    override fun getRelativeLeft(view: View): Int {
        return if (view.parent === view.rootView) {
            view.left
        } else {
            view.left + getRelativeLeft(view.parent as View)
        }
    }

    override fun getRelativeTop(view: View): Int {
        return if (view.parent === view.rootView) {
            view.top
        } else {
            view.top + getRelativeTop(view.parent as View)
        }
    }

    override fun getRelativeRight(view: View): Int {
        return if (view.parent === view.rootView) {
            view.right
        } else {
            view.right + getRelativeRight(view.parent as View)
        }
    }

    override fun getRelativeBottom(view: View): Int {
        return if (view.parent === view.rootView) {
            view.bottom
        } else {
            view.bottom + getRelativeBottom(view.parent as View)
        }
    }

    override fun getScreenLocation(
        view: View,
        rect: Rect
    ) {
        view.getLocationOnScreen(OUT_LOCATION)
        rect.left = OUT_LOCATION[0]
        rect.top = OUT_LOCATION[1]
        rect.right = rect.left + view.measuredWidth
        rect.bottom = rect.top + view.measuredHeight
    }

    override fun getContentRootLocation(
        view: View,
        rect: Rect
    ) {
        view.getDrawingRect(rect)
        container.offsetDescendantRectToMyCoords(view, rect)
    }

    @Px
    override fun toPx(dp: Int): Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp.toFloat(),
            displayMetrics
        ).toInt()
    }

    override fun toDp(@Px px: Int): Int {
        return (px / (displayMetrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)).toInt()
    }

    override fun toSp(px: Float): Int {
        val scaledDensity = displayMetrics.scaledDensity
        return (px / scaledDensity).toInt()
    }

    companion object {
        private val OUT_LOCATION = IntArray(2)
    }

}
