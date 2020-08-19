package com.hjhjw1991.stark.framework

import com.hjhjw1991.stark.plugin.ApplicationExtension
import com.hjhjw1991.stark.plugin.ForegroundManager
import javax.inject.Inject


@AppScope
class ApplicationExtensionImpl @Inject constructor(private val foregroundManager: ForegroundManager) :
    ApplicationExtension {
    override fun getForegroundManager(): ForegroundManager {
        return foregroundManager
    }

}
