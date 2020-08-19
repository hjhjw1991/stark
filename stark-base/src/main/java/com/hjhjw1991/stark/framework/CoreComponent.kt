package com.hjhjw1991.stark.framework

import android.app.Activity
import android.content.ServiceConnection
import android.view.ViewGroup
import com.hjhjw1991.stark.plugin.ActivityResults
import com.hjhjw1991.stark.plugin.AttributeTranslator
import com.hjhjw1991.stark.plugin.MeasurementHelper
import com.hjhjw1991.stark.plugin.PluginModule
import dagger.BindsInstance
import dagger.Component


@ActivityScope
@Component(
    modules = [CoreModule::class, ActivityModule::class],
    dependencies = [AppComponent::class]
)
interface CoreComponent {
    fun inject(view: StarkPluginView?)
    val measurementHelper: MeasurementHelper?
    val attributeTranslator: AttributeTranslator?
    val activity: Activity
    val activityResults: ActivityResults
    val serviceConnection: ServiceConnection?
    val menuController: StarkMenuController
    val pluginModules: Set<PluginModule>

    @Component.Builder
    interface Builder {
        fun appComponent(appComponent: AppComponent?): Builder

        @BindsInstance
        fun pluginSource(pluginSource: PluginSource?): Builder

        @BindsInstance
        fun activity(activity: Activity): Builder

        @BindsInstance
        fun activityResults(activityResults: ActivityResults): Builder

        @BindsInstance
        fun menuController(menuController: StarkMenuController): Builder

        @BindsInstance
        fun container(container: ViewGroup): Builder

        fun build(): CoreComponent
    }
}
