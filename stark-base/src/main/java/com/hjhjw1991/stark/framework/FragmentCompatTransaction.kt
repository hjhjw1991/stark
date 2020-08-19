package com.hjhjw1991.stark.framework

import android.annotation.SuppressLint
import android.app.Fragment
import android.app.FragmentTransaction
import android.support.annotation.IdRes


@SuppressLint("CommitTransaction")
abstract class FragmentCompatTransaction {
    abstract fun add(
        fragment: Any?,
         tag: String?
    ): FragmentCompatTransaction?

    abstract fun add(
        @IdRes containerViewId: Int,
        fragment: Any?,
         tag: String?
    ): FragmentCompatTransaction?

    abstract fun remove(fragment: Any?): FragmentCompatTransaction?
    abstract fun commit()
    private class Android internal constructor(private val transaction: FragmentTransaction) :
        FragmentCompatTransaction() {
        override fun add(
            fragment: Any?,
             tag: String?
        ): FragmentCompatTransaction {
            transaction.add(fragment as Fragment?, tag)
            return this
        }

        override fun add(
            containerViewId: Int,
            fragment: Any?,
             tag: String?
        ): FragmentCompatTransaction {
            transaction.add(containerViewId, fragment as Fragment?, tag)
            return this
        }

        override fun remove(fragment: Any?): FragmentCompatTransaction {
            transaction.remove(fragment as Fragment?)
            return this
        }

        override fun commit() {
            transaction.commit()
        }

    }

    private class Support internal constructor(private val transaction: android.support.v4.app.FragmentTransaction) :
        FragmentCompatTransaction() {
        override fun add(
            fragment: Any?,
             tag: String?
        ): FragmentCompatTransaction {
            transaction.add((fragment as android.support.v4.app.Fragment?)!!, tag)
            return this
        }

        override fun add(
            containerViewId: Int,
            fragment: Any?,
             tag: String?
        ): FragmentCompatTransaction {
            transaction.add(containerViewId, (fragment as android.support.v4.app.Fragment?)!!, tag)
            return this
        }

        override fun remove(fragment: Any?): FragmentCompatTransaction {
            transaction.remove((fragment as android.support.v4.app.Fragment?)!!)
            return this
        }

        override fun commit() {
            transaction.commit()
        }

    }

    companion object {
        fun beginTransaction(transaction: FragmentTransaction): FragmentCompatTransaction {
            return Android(
                transaction
            )
        }

        fun beginTransaction(transaction: android.support.v4.app.FragmentTransaction): FragmentCompatTransaction {
            return Support(
                transaction
            )
        }
    }
}
