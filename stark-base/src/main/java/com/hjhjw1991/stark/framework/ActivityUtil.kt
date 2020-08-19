package com.hjhjw1991.stark.framework

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.View


class ActivityUtil private constructor() {
    companion object {
        
        fun findActivity(view: View): Activity? {
            var v: View = view
            var activity: Activity? =
                findActivity(
                    v.getContext()
                )
            while (activity == null) {
                if (v.parent !is View) {
                    return null
                }
                v = v.getParent() as View
                activity =
                    findActivity(
                        v.getContext()
                    )
            }
            return activity
        }

        
        fun findActivity(context: Context): Activity? {
            var c: Context = context
            // assume the context is a FragmentActivity, or a wrapper around a FragmentActivity
            // keep unwrapping the context until you find a FragmentActivity
            while (c !is Activity) {
                if (c !is ContextWrapper) {
                    // no activity at the bottom of the rabbit hole
                    return null
                }
                val contextWrapper = c
                if (contextWrapper === contextWrapper.baseContext) {
                    // found infinite rabbit hole
                    return null
                }
                // deeper down the rabbit hole we go
                c = contextWrapper.baseContext
            }
            return c
        }
    }

    init {
        throw AssertionError("No instances.")
    }
}