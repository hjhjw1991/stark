package com.hjhjw1991.stark.framework

/**
 * 带参数单例实现, 可复用
 * 定义
 * class ToSingle(arg: Any){
 *   companion object: SingletonHolder<ToSingle, Any> ({arg -> doInit(arg)})
 * }
 * 使用
 * ToSingle.getInstance(arg).xxx
 * @author huangjun.barney
 * @since 2020-05-14
 */
open class SingletonHolder<out T: Any, in A>(creator: (A) -> T) {
    private var creator: ((A)->T)? = creator
    @Volatile private var instance: T? = null

    fun getInstance(arg: A): T {
        val i = instance
        if (i != null) {
            return i
        }

        return synchronized(this) {
            var i2 = instance
            if (i2 != null) {
                i2
            } else {
                val created = creator!!(arg)
                instance = created
                creator = null
                created
            }
        }
    }

}