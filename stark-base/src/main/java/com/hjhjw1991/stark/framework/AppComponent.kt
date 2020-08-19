package com.hjhjw1991.stark.framework

import android.app.Application
import android.content.Context
import com.hjhjw1991.stark.plugin.ApplicationExtension
import dagger.BindsInstance
import dagger.Component

/**
 * @author huangjun.barney
 * @since 2020-05-14
 */

@AppScope
@Component(modules = [AppModule::class])
interface AppComponent {
    val application: Application
    val applicationExtension: ApplicationExtension
    val activityLifecycleCallbacks: Application.ActivityLifecycleCallbacks
    val pluginRepository: PluginRepository
    val publicControl: PublicControl

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun app(application: Application): Builder

        fun build(): AppComponent
    }

    companion object: SingletonHolder<AppComponent, Context?>(
        { context -> DaggerAppComponent.builder().app(context?.applicationContext as Application).build() } )
}