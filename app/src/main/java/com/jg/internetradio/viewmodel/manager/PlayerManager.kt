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
    private val bandwidthMeter = DefaultBandwidthMeter()
    private val trackSelectionFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)
    private val trackSelector = DefaultTrackSelector(trackSelectionFactory)

    private var player: SimpleExoPlayer? = null

    private val dataSourceFactory = DefaultDataSourceFactory(context, Util.getUserAgent(context, "InternetRadio"))
    private var mediaSource: MediaSource? = null

    val isPlaying: Boolean
            get() = player != null && player?.playWhenReady == true

    fun play(mediaSourceURI: String) {
        if (isPlaying) {
            stop()
        }

        player = ExoPlayerFactory.newSimpleInstance(context, trackSelector)
        mediaSource = ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(Uri.parse(mediaSourceURI))

        player?.prepare(mediaSource)
        player?.playWhenReady = true
    }

    fun pause() {
        player?.playWhenReady = false
    }

    fun stop() {
        player?.playWhenReady = false
        player?.stop()
        player?.release()
        player = null
        mediaSource = null
    }
}