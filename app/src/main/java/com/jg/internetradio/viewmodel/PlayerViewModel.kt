package com.jg.internetradio.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel

import com.jg.internetradio.entity.Station
import com.jg.internetradio.repository.PlayerLiveData

import com.jg.internetradio.viewmodel.manager.PlayerManager

class PlayerViewModel(application: Application) : AndroidViewModel(application) {

//    class Factory(private val application: Application, private val station: Station) : ViewModelProvider.Factory {
//        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//            return PlayerViewModel(application) as T
//        }
//    }

    private val playerManager: PlayerManager = PlayerManager(application.applicationContext)
    val observableStation: PlayerLiveData<Station> = PlayerLiveData()

    fun play() = playerManager.play(observableStation.station?.streams?.first()?.stream!!)

    fun pause() = playerManager.pause()

    fun stop() = playerManager.stop()

}