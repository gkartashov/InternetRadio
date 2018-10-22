package com.jg.internetradio.viewmodel

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.support.v4.media.app.NotificationCompat as MediaNotificationCompat
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationCompat
import com.jg.internetradio.R
import com.jg.internetradio.viewmodel.player.PlayerAction.*
import com.jg.internetradio.viewmodel.service.PlayerServiceIntentEntry

class PlayerNotification(private val context: Context) {
    private var requestCode = 0
        get() = field++

    private var playerServiceIntentEntry: PlayerServiceIntentEntry? = null

    val notificationId = "Dirble App".hashCode()

    private var channelId: String = ""

    private var builder: NotificationCompat.Builder

    private var notificationManager: NotificationManager =
        context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            channelId = createChannelId()
        }
        builder = NotificationCompat.Builder(context, channelId)
    }

    fun build(): Notification = builder.build()

    fun create() {
        builder
            .setOngoing(true)
            .setSmallIcon(R.drawable.ic_note)
            .setContentIntent(createPendingIntent(requestCode) {
                action = SHOW_PLAYER.name
            })
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
    }

    fun update(intentEntry: PlayerServiceIntentEntry) {
        val isPlaying = intentEntry.action == "PLAY"

        playerServiceIntentEntry = intentEntry
        builder
            .setContentTitle(playerServiceIntentEntry?.stationName)
            .setContentText(playerServiceIntentEntry?.categories)
            .setStyle(
                MediaNotificationCompat.MediaStyle()
                    .setShowActionsInCompactView(0, 1, 2)
            )
        builder.mActions = addActions(isPlaying)
    }

    fun notify(notification: Notification) =
        notificationManager.notify(notificationId, notification)

    fun cancel() = notificationManager.cancel(notificationId)

    private fun addActions(isPlaying: Boolean): ArrayList<NotificationCompat.Action> {
        val playPauseActionIcon = if (isPlaying) R.drawable.ic_pause else R.drawable.ic_play
        val playPauseActionState = if (isPlaying) PAUSE.name else PLAY.name

        val stationListAction = createNotificationCompatAction(
            R.drawable.ic_stationlist,
            "Show stations list"
        ) {
            action = SHOW_STATIONS_SOURCE_LIST.name
        }

        val playPauseAction = createNotificationCompatAction(
            playPauseActionIcon,
            "Play/Pause"
        ) {
            putExtra("MEDIA_SOURCE_URI", playerServiceIntentEntry?.mediaSourceUri ?: "")
            action = playPauseActionState
        }

        val stopAction = createNotificationCompatAction(
            R.drawable.ic_stop,
            "Stop"
        ) {
            action = STOP.name
        }

        return arrayListOf(stationListAction, playPauseAction, stopAction)
    }

    private fun createNotificationCompatAction(
        icon: Int,
        title: CharSequence,
        actionLambda: Intent.() -> Unit
    ) = NotificationCompat.Action(icon, title, createPendingIntent(requestCode, actionLambda))

    private fun createPendingIntent(requestCode: Int, action: Intent.() -> Unit): PendingIntent {
        val intent: Intent = Intent().apply(action)
        return PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_CANCEL_CURRENT
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createChannelId(): String =
        createNotificationChannel("player_notification", "Player notification")

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String {
        val channel =
            NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_NONE)
        channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        notificationManager.createNotificationChannel(channel)
        return channelId
    }
}