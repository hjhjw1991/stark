package com.hjhjw1991.stark.framework

import android.app.Application
import com.hjhjw1991.stark.plugin.ApplicationExtension
import com.hjhjw1991.stark.plugin.ForegroundManager
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet


/**
 * App模块, 绑定各种实现
 * @author huangjun.barney
 * @since 2020-05-14
 */
@Module
abstract class AppModule {
    // dagger below 2.26 we need @Module on companion for provides
    @Module
    companion object{
        @JvmStatic
        @Provides
        fun providePluginSource(container: CoreComponentContainer): PluginSource {
            return container.pluginSource
        }
    }

    @AppScope
    @Binds
    abstract fun bindFilter(filter: StarkIgnoreFilter): Application.ActivityLifecycleCallbacks

    @AppScope
    @Binds
    abstract fun bindApplicationExtension(extension: ApplicationExtensionImpl?): ApplicationExtension

    @AppScope
    @Binds
    abstract fun bindPublicControl(control: PublicControlImpl?): PublicControl

    @AppScope
    @Binds
    abstract fun bindForegroundManager(foregroundManager: ForegroundManagerImpl?): ForegroundManager

    @AppScope
    @Binds
    @IntoSet
    abstract fun bindServiceDelegate(delegate: StarkServiceLifecycleDelegate): StarkLifecycleDelegate

    @AppScope
    @Binds
    @IntoSet
    abstract fun bindComponentDelegate(delegate: InstallationLifecycleDelegate): StarkLifecycleDelegate

    @AppScope
    @Binds
    @IntoSet
    abstract fun bindForegroundDelegate(delegate: ForegroundManagerLifecycleDelegate): StarkLifecycleDelegate
}