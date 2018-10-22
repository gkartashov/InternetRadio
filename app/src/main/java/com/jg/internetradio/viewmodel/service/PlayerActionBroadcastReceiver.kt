package com.jg.internetradio.viewmodel.service

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.jg.internetradio.ui.activity.MainActivity
import com.jg.internetradio.viewmodel.player.PlayerAction
import com.jg.internetradio.viewmodel.player.StationPlayer

class PlayerActionBroadcastReceiver(private var stationPlayer: StationPlayer) :
    BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val playerAction = intent?.action ?: "STOP"

        when (playerAction) {
            "PLAY" -> {
                val mediaSourceUri: String = intent?.getStringExtra("MEDIA_SOURCE_URI") ?: ""
                stationPlayer.play(mediaSourceUri)
            }
            "PAUSE" -> {
                stationPlayer.pause()
            }
            "STOP" -> {
                stationPlayer.stop()
            }
            "SHOW_PLAYER" -> {
                val showPlayerPendingIntent: PendingIntent = PendingIntent.getActivity(
                    context,
                    0,
                    Intent(context, MainActivity::class.java).apply {
                        action = PlayerAction.SHOW_PLAYER.name
                    },
                    0
                )
                showPlayerPendingIntent.send()
            }
            "SHOW_STATIONS_SOURCE_LIST" -> {
                val showStationsSourceListPendingIntent: PendingIntent = PendingIntent.getActivity(
                    context,
                    0,
                    Intent(context, MainActivity::class.java).apply {
                        action = PlayerAction.SHOW_STATIONS_SOURCE_LIST.name
                    },
                    0
                )
                showStationsSourceListPendingIntent.send()
            }
        }
    }

}