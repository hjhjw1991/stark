package com.hjhjw1991.stark.framework

import android.text.SpannableStringBuilder

/**
 * spannable debug logger
 * @author huangjun.barney
 * @since 2020-06-03
 */
interface StarkDebugLogger {
    val message: SpannableStringBuilder
    fun log(msg: CharSequence?, type: LogType?)
    fun logd(msg: CharSequence?)
    fun logd(tag: CharSequence, msg: CharSequence?)
    fun logd(tag: CharSequence, msg: CharSequence?, type: LogType?)

    enum class LogType {
        INFO,
        DEBUG,
        ERROR
    }
}