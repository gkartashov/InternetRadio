package com.jg.internetradio.viewmodel

import android.app.Application
import android.arch.lifecycle.*

import com.jg.internetradio.entity.Station
import com.jg.internetradio.repository.PlayerLiveData
import com.jg.internetradio.viewmodel.manager.PlayerManager

class PlayerViewModel(application: Application) : AndroidViewModel(application), LifecycleObserver {
    val isStationAdded = MutableLiveData<Boolean>()
    val isPlaying = MutableLiveData<Boolean>()

    private val playerManager = PlayerManager(application.applicationContext)
    private val observableStation = PlayerLiveData()

    init {
        isPlaying.value = false
        isStationAdded.value = false
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun play() {
        if (observableStation.isReadyToPlay) {
            playerManager.play(observableStation.getFirstStream()!!)
            isPlaying.value = playerManager.isPlaying
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
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

    fun observeToStation(lifecycleOwner: LifecycleOwner, observer: Observer<Station>) {
        observableStation.observe(lifecycleOwner, observer)
    }

    fun getCategoriesAsString() = observableStation.getCategoriesAsString()

    fun getStationName() = observableStation.getStationName()

    fun getStationImageUrl() = observableStation.getStationImageUrl()

}