package com.hjhjw1991.stark.framework

import android.app.Activity
import android.view.ViewGroup
import com.hjhjw1991.stark.plugin.*


class PluginExtensionImpl(private val component: CoreComponent) :
    PluginExtension {
    
    override fun getActivity(): Activity {
        return component.activity
    }

    
    override fun getContentRoot(): ViewGroup {
        return component.menuController.contentView as ViewGroup
    }

    
    override fun getOverlayContainer(): OverlayContainer {
        return component.menuController
    }

    
    override fun getActivityResults(): ActivityResults {
        return component.activityResults
    }

    
    override fun getAttributeTranslator(): AttributeTranslator? {
        return component.attributeTranslator
    }

    
    override fun getMeasurementHelper(): MeasurementHelper? {
        return component.measurementHelper
    }

    override val starkMenu: StarkMenu
        get() = component.menuController

}
