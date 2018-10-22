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

    private val additionalStationPlayerAction: (Boolean) -> Unit = {
        isPlaying.value = it
    }

    private val removeSourceLambda: () -> Unit = {
        removeSource()
    }

    init {
        isPlaying.value = false
        isStationAdded.value = false
        playerManager.setRemoveSourceAction(removeSourceLambda)
    }

    fun play() {
        if (observableStation.isReadyToPlay) {
            observableStation.value?.let {
                playerManager.play(it, additionalStationPlayerAction)
            }
        }
    }

    override fun onCleared() {
        playerManager.unbindService()
        super.onCleared()
    }

    fun pause() {
        playerManager.pause()
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

    private fun stop() {
        playerManager.stop()
        isPlaying.value = false
    }

    fun observeToStation(lifecycleOwner: LifecycleOwner, observer: Observer<Station>) =
        observableStation.observe(lifecycleOwner, observer)

    fun getCategoriesAsString() = observableStation.getCategoriesAsString()

    fun getStationName() = observableStation.getStationName()

    fun getStationImageUrl() = observableStation.getStationImageUrl()

}