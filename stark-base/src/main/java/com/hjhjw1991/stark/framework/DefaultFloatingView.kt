package com.hjhjw1991.stark.framework

import android.content.Context
import android.support.annotation.DrawableRes
import android.support.annotation.LayoutRes
import android.view.View
import android.widget.ImageView
import com.hjhjw1991.stark.stark.R


/**
 * @author huangjun.barney
 * @since 2020-06-01
 */

class DefaultFloatingView(context: Context, @LayoutRes resource: Int) :
    StarkFloatingView(context, null) {
    private val mIcon: ImageView

    constructor(context: Context) : this(context, R.layout.stark_floating_view) {}

    fun setIconImage(@DrawableRes resId: Int) {
        mIcon.setImageResource(resId)
    }

    init {
        View.inflate(context, resource, this)
        mIcon = findViewById(R.id.icon)
    }
}
