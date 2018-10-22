package com.jg.internetradio.viewmodel.manager

import android.content.ComponentName
import android.content.Context
import android.content.Context.BIND_AUTO_CREATE
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import com.jg.internetradio.entity.Station
import com.jg.internetradio.viewmodel.player.PlayerAction.*
import com.jg.internetradio.viewmodel.service.PlayerService

class PlayerManager(private val context: Context) {
    private var isRegistered = false

    private var playerService: PlayerService? = null

    private val stopAction: () -> Unit = {
        stop()
        removeSourceAction()
    }

    private lateinit var removeSourceAction: () -> Unit

    private lateinit var stationPlayerActionLambda: (Boolean) -> Unit

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {

        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            val binder = service as? PlayerService.PlayerServiceBinder
            playerService = binder?.getService()
            playerService?.setOnStateChangedAction(stationPlayerActionLambda)
            playerService?.setOnStopAction(stopAction)
        }
    }

    fun setRemoveSourceAction(lambda: () -> Unit) {
        removeSourceAction = lambda
    }

    fun play(station: Station, stationPlayerActionLambda: (Boolean) -> Unit) {
        this.stationPlayerActionLambda = stationPlayerActionLambda

        if (playerService == null) {
            bindService()
        }

        val intent = buildServiceIntent {
            putExtra("MEDIA_SOURCE_URI", station.getFirstStreamFromList() ?: "")
            putExtra("STATION_NAME", station.name)
            putExtra("CATEGORIES", station.categoriesToString())
            action = PLAY.name
        }

        context.startService(intent)
        isRegistered = true
    }

    private fun bindService() {
        context.bindService(
            buildServiceIntent { action = CREATE.name },
            serviceConnection,
            BIND_AUTO_CREATE
        )
    }

    fun pause() {
        val intent = buildServiceIntent {
            action = PAUSE.name
        }
        context.startService(intent)
    }

    fun unbindService() {
        if (isRegistered) {
            context.unbindService(serviceConnection)
        }
    }

    fun stop() {
        val intent = buildServiceIntent { }
        unbindService()
        context.stopService(intent)
        isRegistered = false
        playerService = null
    }

    private fun buildServiceIntent(action: Intent.() -> Unit): Intent =
        Intent(context, PlayerService::class.java).apply(action)
}