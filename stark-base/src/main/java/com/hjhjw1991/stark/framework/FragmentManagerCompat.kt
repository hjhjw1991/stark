package com.hjhjw1991.stark.framework

import android.annotation.SuppressLint
import android.app.Activity
import android.support.v7.app.AppCompatActivity


abstract class FragmentManagerCompat {
    val isSupport: Boolean
        get() = this is Support

    abstract fun <T> findFragmentByTag(tag: String?): T
    abstract fun beginTransaction(): FragmentCompatTransaction
    internal class Android internal constructor(private val activity: Activity) :
        FragmentManagerCompat() {
        
        override fun <T> findFragmentByTag(tag: String?): T {
            return activity.fragmentManager.findFragmentByTag(tag) as T
        }

        @SuppressLint("CommitTransaction")
        override fun beginTransaction(): FragmentCompatTransaction {
            return FragmentCompatTransaction.beginTransaction(
                activity.fragmentManager.beginTransaction()
            )
        }

    }

    internal class Support internal constructor(private val activity: AppCompatActivity) :
        FragmentManagerCompat() {
        
        override fun <T> findFragmentByTag(tag: String?): T {
            return activity.supportFragmentManager.findFragmentByTag(tag) as T
        }

        @SuppressLint("CommitTransaction")
        override fun beginTransaction(): FragmentCompatTransaction {
            return FragmentCompatTransaction.beginTransaction(
                activity.supportFragmentManager.beginTransaction()
            )
        }

    }

    companion object {
        fun create(activity: Activity): FragmentManagerCompat {
            return if (activity is AppCompatActivity) {
                Support(
                    activity
                )
            } else {
                Android(
                    activity
                )
            }
        }
    }
}
