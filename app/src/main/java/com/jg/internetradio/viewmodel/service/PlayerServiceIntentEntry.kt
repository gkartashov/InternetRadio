package com.jg.internetradio.viewmodel.service

import android.content.Intent

class PlayerServiceIntentEntry private constructor(
    val mediaSourceUri: String,
    var action: String,
    val stationName: String,
    val categories: String
) {
    companion object {
        fun create(intent: Intent? = null): PlayerServiceIntentEntry {
            val mediaSourceUri = intent?.getStringExtra("MEDIA_SOURCE_URI") ?: ""
            val categories = intent?.getStringExtra("CATEGORIES") ?: ""
            val stationName = intent?.getStringExtra("STATION_NAME") ?: ""
            val actionString = intent?.action ?: "STOP"
            return PlayerServiceIntentEntry(mediaSourceUri, actionString, stationName, categories)
        }
    }

    fun isEmpty() = mediaSourceUri.isBlank()
}