package com.hjhjw1991.stark.framework

import android.content.Intent
import android.support.v4.app.Fragment

import com.hjhjw1991.stark.plugin.ActivityResults


class ActivityResultsSupportFragment : Fragment(),
    ActivityResults {
    private val listeners: MutableList<ActivityResults.Listener?> =
        ArrayList()

    override fun register(listener: ActivityResults.Listener?) {
        listeners.add(listener)
    }

    override fun unregister(listener: ActivityResults.Listener?): Boolean {
        return listeners.remove(listener)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        for (listener in listeners) {
            listener!!.onActivityResult(requestCode, resultCode, data)
        }
    }
}
