package com.jg.internetradio.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer

import com.jg.internetradio.entity.Station
import com.jg.internetradio.repository.PlayerLiveData
import com.jg.internetradio.viewmodel.manager.PlayerManager

class PlayerViewModel(application: Application) : AndroidViewModel(application) {

    private val playerManager: PlayerManager = PlayerManager(application.applicationContext)
    private val observableStation: PlayerLiveData<Station> = PlayerLiveData()

    val isPlaying: Boolean
        get() = playerManager.isPlaying

    fun play() = playerManager.play(observableStation.value?.streams?.first()?.stream!!)

    fun pause() = playerManager.pause()

    fun stop() = playerManager.stop()

    fun addSource(station: Station) = observableStation.addDataSource(station)

    fun removeSource() = observableStation.removeDataSource()

    fun observeToStation(lifecycleOwner: LifecycleOwner, observer: Observer<Station>) = observableStation.observe(lifecycleOwner, observer)

    fun categoriesToString() = observableStation.value?.categoryListToString()

}