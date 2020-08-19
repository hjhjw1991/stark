package com.hjhjw1991.stark.framework

import javax.inject.Scope

/**
 * App scoped instance
 * @author huangjun.barney
 * @since 2020-05-14
 */
@Scope
@Target(AnnotationTarget.CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityScope