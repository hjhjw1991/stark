package com.hjhjw1991.stark.framework

import android.support.annotation.Px
import com.hjhjw1991.stark.plugin.AttributeTranslator
import com.hjhjw1991.stark.plugin.MeasurementHelper
import javax.inject.Inject


class AttributeTranslatorImpl @Inject constructor(private val measurementHelper: MeasurementHelper) :
    AttributeTranslator {
    override fun translateDp(dp: Int): String {
        return dp.toString() + " dp, " + measurementHelper.toPx(dp) + " px"
    }

    override fun translatePx(@Px px: Int): String {
        return measurementHelper.toDp(px).toString() + " dp, " + px + " px"
    }

    override fun translatePxToSp(px: Int): String {
        return measurementHelper.toSp(px.toFloat()).toString() + " sp, " + px + " px"
    }

}
