package com.hjhjw1991.stark.plugin

/**
 * 用[StarkIgnore]标记的类, Stark将忽略对其注入自己的view, 这对于插件本身会打开Activity的情况十分有用
 * Annotate [android.app.Activity] subclasses with this to prevent Stark from embedding itself
 * in its layout.
 *
 * This can be useful for plugins that wish to start their own [android.app.Activity]
 * subclass as part of their functionality.
 *
 * @author huangjun.barney
 * @since 2020-05-15
 *
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class StarkIgnore