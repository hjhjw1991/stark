package com.hjhjw1991.stark.framework

import javax.inject.Provider
import dagger.Lazy


class Cached<T>(private val provider: Provider<T?>) : Lazy<T?> {
    private var item: T? = null
    override fun get(): T? {
        if (item == null) {
            item = provider.get()
        }
        return item
    }

}
