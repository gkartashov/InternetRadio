package com.jg.internetradio.viewmodel.service

import android.app.*
import android.content.Intent
import android.content.IntentFilter
import android.os.Binder
import android.support.v4.media.app.NotificationCompat as MediaNotificationCompat
import android.os.IBinder
import com.jg.internetradio.viewmodel.PlayerNotification
import com.jg.internetradio.viewmodel.player.PlayerAction
import com.jg.internetradio.viewmodel.player.PlayerAction.*
import com.jg.internetradio.viewmodel.player.StationPlayer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject

class PlayerService : Service() {
    private var isForeground = false

    private lateinit var intentEntry: PlayerServiceIntentEntry

    private lateinit var onStopAction: () -> Unit
    private lateinit var onStateChangedAction: (Boolean) -> Unit

    private val stationPlayerBinder = PlayerServiceBinder()

    private val intentEntrySubject = PublishSubject.create<PlayerServiceIntentEntry>()

    private lateinit var subscriptionToUpdates: Disposable
    private lateinit var subscriptionToStateChanges: Disposable

    private lateinit var playerActionBroadcastReceiver: PlayerActionBroadcastReceiver

    private lateinit var stationPlayer: StationPlayer

    private lateinit var intentFilter: IntentFilter

    private lateinit var notification: PlayerNotification

    private val playerStateChangesLambda: (PlayerServiceIntentEntry) -> Unit = {
        val broadcastIntent = Intent().apply {
            putExtra("MEDIA_SOURCE_URI", it.mediaSourceUri)
            action = it.action
        }
        sendBroadcast(broadcastIntent)
    }

    override fun onCreate() {
        stationPlayer = StationPlayer(applicationContext)
        notification = PlayerNotification(this)
        notification.create()
        intentFilter = initIntentFilter()
        playerActionBroadcastReceiver = PlayerActionBroadcastReceiver(stationPlayer)
        registerReceiver(playerActionBroadcastReceiver, intentFilter)
        subscriptionToUpdates = subscribeForUpdates()
        subscriptionToStateChanges = subscribeToStateChanges()
        super.onCreate()
    }

    private fun initIntentFilter() = IntentFilter().apply {
        for (action in PlayerAction.values()) {
            addAction(action.name)
        }
    }

    private fun subscribeForUpdates() = intentEntrySubject
        .subscribeOn(Schedulers.newThread())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(playerStateChangesLambda)

    private fun subscribeToStateChanges() =
        stationPlayer.playerState.subscribe {
            intentEntry.action = it.name
            onStateChangedAction(!(it == STOP || it == PAUSE))
            if (it == STOP) {
                notification.cancel()
                onStopAction()
            } else {
                notification.update(intentEntry)
                notification.notify(notification.build())
            }
        }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val newIntentEntry = PlayerServiceIntentEntry.create(intent)
        if (!newIntentEntry.isEmpty()) {
            intentEntry = newIntentEntry
        } else {
            intentEntry.action = newIntentEntry.action
        }

        intentEntrySubject.onNext(intentEntry)

        notification.update(intentEntry)

        if (!isForeground)
            startForeground(notification.notificationId, notification.build())

        return super.onStartCommand(intent, flags, startId)
    }

    fun setOnStateChangedAction(lambda: (Boolean) -> Unit) {
        onStateChangedAction = lambda
    }

    fun setOnStopAction(lambda: () -> Unit) {
        onStopAction = lambda
    }

    override fun onBind(intent: Intent?): IBinder? {
        return stationPlayerBinder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        disposeWithCheck(subscriptionToUpdates)
        disposeWithCheck(subscriptionToStateChanges)
        return super.onUnbind(intent)
    }

    private fun disposeWithCheck(disposable: Disposable) {
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
    }

    override fun onDestroy() {
        stationPlayer.stop()
        unregisterReceiver(playerActionBroadcastReceiver)
        stopForeground(true)
        isForeground = false
        disposeWithCheck(subscriptionToUpdates)
        disposeWithCheck(subscriptionToStateChanges)
        super.onDestroy()
    }

    inner class PlayerServiceBinder : Binder() {
        fun getService(): PlayerService {
            return this@PlayerService
        }
    }
}