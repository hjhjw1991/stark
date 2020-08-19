package com.hjhjw1991.stark.framework

import android.app.*
import android.content.*
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.support.v4.app.NotificationCompat
import com.hjhjw1991.stark.stark.R
import com.hjhjw1991.stark.util.StarkGlobalSp
import java.lang.ref.WeakReference
import javax.inject.Inject

/**
 * we can use this service to open stark when floating window is not a choice
 * @author huangjun.barney
 * @since 2020-05-15
 */

class StarkService : Service() {
    private val binder: IBinder? = Binder()
    private val actionOpenMenuReceiver: BroadcastReceiver? = OpenMenuReceiver()
    private var notificationManager: NotificationManager? = null
    private var activity: WeakReference<Activity?>? = null
    fun attach(activity: Activity?) {
        this.activity = WeakReference(activity)
        initChannels()
        startForeground(NOTIFICATION_ID, createNotification(activity))
    }

    fun detach(activity: Activity?) {
        this.activity?.let {
            if (it.get() == activity) {
                stopForeground(true)
                this.activity = null
            }
        }
    }

    private fun createNotification(activity: Activity?): Notification? {
        val builder: NotificationCompat.Builder =
            NotificationCompat.Builder(this,
                CHANNEL_ID
            )
                .setContentIntent(createContentPendingIntent())
                .setTicker("")
                .setSmallIcon(R.drawable.stark_logo)
                .setOngoing(true)
                .setVibrate(longArrayOf(0))
        val contentTitle: String = getString(R.string.stark_notification_content_title)
        val contentText: String = getString(R.string.stark_notification_content_text)
        val subText: String = getString(R.string.stark_notification_subtext)
        if (Build.VERSION.SDK_INT === Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1) {
            builder.setContentTitle(contentTitle)
            builder.setContentText(if (contentText.isEmpty()) subText else contentText)
        } else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            builder.setContentTitle(contentTitle)
            builder.setContentText(contentText)
            builder.setSubText(subText)
        } else {
            builder.setContentTitle(if (contentTitle.isEmpty()) null else contentTitle)
            builder.setContentText(if (contentText.isEmpty()) null else contentText)
            builder.setSubText(subText)
        }
        return builder.build()
    }

    private fun createContentPendingIntent(): PendingIntent? {
        return PendingIntent.getBroadcast(
            this,
            ACTION_REQUEST_CODE_OPEN_MENU,
            Intent(ACTION_OPEN_MENU), PendingIntent.FLAG_CANCEL_CURRENT
        )
    }

    private fun initChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                getString(R.string.stark_notification_channel_name),
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = getString(R.string.stark_notification_channel_description)
            channel.enableLights(true)
            channel.lightColor = Color.BLUE
            channel.enableVibration(false)
            channel.importance = NotificationManager.IMPORTANCE_LOW
            notificationManager?.createNotificationChannel(channel)
        }
    }

    override fun onCreate() {
        super.onCreate()
        notificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager?
        registerReceiver(
            actionOpenMenuReceiver,
            IntentFilter(ACTION_OPEN_MENU)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(actionOpenMenuReceiver)
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    internal inner class OpenMenuReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            activity?.get().let {
                AppComponent.getInstance(this@StarkService).publicControl.open(it)
            }
        }
    }

    internal inner class Binder : android.os.Binder() {
        val service: StarkService?
            get() = this@StarkService
    }

    @ActivityScope
    class Connection @Inject constructor(private val activity: Activity) :
        ServiceConnection {
        private var service: StarkService? = null
        override fun onServiceConnected(
            name: ComponentName?,
            service: IBinder?
        ) {
            // 低版本手机上关闭service, 避免造成anr
            if (StarkGlobalSp.isStarkServiceEnabled() && Build.VERSION.SDK_INT > Build.VERSION_CODES.JELLY_BEAN_MR2) {
                this.service = (service as? Binder)?.service
                this.service?.attach(activity)
            }
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            service?.detach(activity)
            service = null
        }

    }

    companion object {
        private const val NOTIFICATION_ID = 0x400
        private const val CHANNEL_ID: String = "stark-activation-channel"
        private const val ACTION_REQUEST_CODE_OPEN_MENU = 0x100
        private const val ACTION_OPEN_MENU: String = "open-menu"
    }
}