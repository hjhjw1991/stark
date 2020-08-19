package com.hjhjw1991.stark.framework

import android.app.Activity
import android.os.Bundle
import android.view.ViewGroup
import com.hjhjw1991.stark.plugin.ActivityResults
import javax.inject.Inject


@AppScope
class InstallationLifecycleDelegate @Inject constructor(
    private val container: CoreComponentContainer,
    private val applicationInstaller: ApplicationInstaller
) : StarkLifecycleDelegate() {
    // 当Activity创建时, 将Stark的浮窗界面注入到Activity中, 由此可以避免申请浮窗权限
    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        applicationInstaller.installIfNeeded()
        val windowContentView =
            activity?.window?.findViewById<ViewGroup>(android.R.id.content)
        val controller = StarkMenuController(
            windowContentView!!
        )
        val fragmentManager: FragmentManagerCompat =
            FragmentManagerCompat.create(
                activity
            )
        var activityResults: ActivityResults? =
            fragmentManager.findFragmentByTag(ACTIVITY_RESULT_TAG)
        if (activityResults == null) {
            activityResults =
                if (fragmentManager.isSupport) ActivityResultsSupportFragment() else ActivityResultsFragment()
            fragmentManager.beginTransaction()
                .add(activityResults,
                    ACTIVITY_RESULT_TAG
                )
                ?.commit()
        }
        val component: CoreComponent = DaggerCoreComponent.builder()
            .appComponent(AppComponent.getInstance(activity))
            .activity(activity)
            .pluginSource(container.pluginSource)
            .menuController(controller)
            .container(windowContentView)
            .activityResults(activityResults)
            .build()
        activity.let { container.putComponent(it, component) }

        // embed plugins list into menu
        val pluginView =
            StarkPluginView(
                ComponentContextThemeWrapper(
                    activity,
                    component
                )
            )
        controller.setPluginView(pluginView)
    }

    override fun onActivityDestroyed(activity: Activity?) {
        container.removeComponent(activity)
    }

    companion object {
        private const val ACTIVITY_RESULT_TAG = "stark_activity_result"
    }

}
