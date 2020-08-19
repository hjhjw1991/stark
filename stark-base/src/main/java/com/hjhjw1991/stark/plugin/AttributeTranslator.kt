package com.hjhjw1991.stark.plugin

import android.support.annotation.Px

interface AttributeTranslator {
    fun translateDp(dp: Int): String

    fun translatePx(@Px px: Int): String

    fun translatePxToSp(px: Int): String
}
