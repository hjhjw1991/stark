package com.hjhjw1991.stark.framework

import android.content.ServiceConnection
import com.hjhjw1991.stark.plugin.AttributeTranslator
import com.hjhjw1991.stark.plugin.MeasurementHelper
import dagger.Binds
import dagger.Module


@Module
internal abstract class CoreModule {
    @Binds
    @ActivityScope
    abstract fun bindServiceConnection(connection: StarkService.Connection?): ServiceConnection?

    @Binds
    @ActivityScope
    abstract fun bindMeasurementHelper(measurementHelper: MeasurementHelperImpl?): MeasurementHelper?

    @Binds
    @ActivityScope
    abstract fun bindAttributeTranslator(attributeTranslator: AttributeTranslatorImpl?): AttributeTranslator?
}
