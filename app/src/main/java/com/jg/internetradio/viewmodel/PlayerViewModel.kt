package com.jg.internetradio.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer

import com.jg.internetradio.entity.Station
import com.jg.internetradio.repository.PlayerLiveData
import com.jg.internetradio.viewmodel.manager.PlayerManager

class PlayerViewModel(application: Application) : AndroidViewModel(application) {

    private val playerManager: PlayerManager = PlayerManager(application.applicationContext)
    private val observableStation: PlayerLiveData<Station> = PlayerLiveData()

    val isStationAdded = MutableLiveData<Boolean>()
    val isPlaying = MutableLiveData<Boolean>()

    init {
        isPlaying.value = false
        isStationAdded.value = false
    }

    fun play() {
        playerManager.play(observableStation.value?.streams?.first()?.stream!!)
        isPlaying.value = playerManager.isPlaying
    }

    fun pause() = playerManager.pause()

    fun stop() {
        playerManager.stop()
        isPlaying.value = false
    }

    fun addSource(station: Station) {
        observableStation.addDataSource(station)
        isStationAdded.value = true
    }

    fun removeSource() {
        stop()
        observableStation.removeDataSource()
        isStationAdded.value = false
    }

    fun observeToStation(lifecycleOwner: LifecycleOwner, observer: Observer<Station>) = observableStation.observe(lifecycleOwner, observer)

    fun categoriesToString() = observableStation.value?.categoryListToString()

    fun stationName() = observableStation.value?.name

    fun stationImageUrl() = observableStation.value?.image?.url

}