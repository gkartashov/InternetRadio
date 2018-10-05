package com.jg.internetradio.viewmodel.manager

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util

class PlayerManager(private val context: Context) {
    val isPlaying: Boolean
        get() = player != null && player?.playWhenReady == true

    private val bandwidthMeter = DefaultBandwidthMeter()
    private val trackSelectionFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)
    private val trackSelector = DefaultTrackSelector(trackSelectionFactory)

    private var player: SimpleExoPlayer? = null

    private val dataSourceFactory = DefaultDataSourceFactory(context, Util.getUserAgent(context, "InternetRadio"))
    private var mediaSource: MediaSource? = null

    fun play(mediaSourceURI: String = "") {
        if (isPlaying) {
            playNewMediaSource(mediaSourceURI)
        } else {
            player = initializePlayer()
            if (mediaSourceURI.isNotEmpty())
                mediaSource = initializeMediaSource(mediaSourceURI)
            startPlayback()
        }
    }

    private fun playNewMediaSource(mediaSourceURI: String) {
        clearMediaSource()
        mediaSource = initializeMediaSource(mediaSourceURI)
        startPlayback()
    }

    private fun clearMediaSource() {
        mediaSource = null
    }

    private fun initializeMediaSource(mediaSourceURI: String): MediaSource {
        val preparedMediaSource = Uri.parse(mediaSourceURI)
        return ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(preparedMediaSource)
    }

    private fun startPlayback() {
        player?.prepare(mediaSource)
        player?.playWhenReady = true
    }

    private fun initializePlayer() = ExoPlayerFactory.newSimpleInstance(context, trackSelector)

    fun stop() {
        player?.playWhenReady = false
        player?.stop()

        releasePlayerResources()
        clearMediaSource()
    }

    private fun releasePlayerResources() {
        player?.release()
        player = null
    }


}