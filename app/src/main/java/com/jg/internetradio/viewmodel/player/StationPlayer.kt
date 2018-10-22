package com.jg.internetradio.viewmodel.player

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
import io.reactivex.subjects.PublishSubject

class StationPlayer(private val context: Context) {
    val playerState = PublishSubject.create<PlayerAction>()

    private val bandwidthMeter = DefaultBandwidthMeter()
    private val trackSelectionFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)
    private val trackSelector = DefaultTrackSelector(trackSelectionFactory)

    private var player: SimpleExoPlayer? = null

    private val dataSourceFactory =
        DefaultDataSourceFactory(context, Util.getUserAgent(context, "InternetRadio"))
    private var mediaSource: MediaSource? = null

    fun play(mediaSourceURI: String = "") {
        if (player != null && player?.playWhenReady == true) {
            playNewMediaSource(mediaSourceURI)
        } else {
            player = initializePlayer()
            if (mediaSourceURI.isNotEmpty()) {
                mediaSource = initializeMediaSource(mediaSourceURI)
                startPlayback()
                playerState.onNext(PlayerAction.PLAY)
            }
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
        return ExtractorMediaSource.Factory(dataSourceFactory)
            .createMediaSource(preparedMediaSource)
    }

    private fun startPlayback() {
        player?.prepare(mediaSource)
        player?.playWhenReady = true
    }

    private fun initializePlayer() = ExoPlayerFactory.newSimpleInstance(context, trackSelector)

    fun pause() {
        player?.playWhenReady = false
        player?.stop()

        playerState.onNext(PlayerAction.PAUSE)

        releasePlayerResources()
        clearMediaSource()
    }

    fun stop() {
        player?.playWhenReady = false
        player?.stop()

        playerState.onNext(PlayerAction.STOP)

        releasePlayerResources()
        clearMediaSource()
    }

    private fun releasePlayerResources() {
        player?.release()
        player = null
    }
}