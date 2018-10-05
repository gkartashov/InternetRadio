package com.jg.internetradio.repository

import android.arch.lifecycle.LiveData
import com.jg.internetradio.entity.Station

class PlayerLiveData : LiveData<Station>() {
    val isReadyToPlay: Boolean
        get() = value != null && value?.isStreamListNotEmptyOrNull ?: false

    fun addDataSource(dataSource: Station) {
        value = dataSource
    }

    fun removeDataSource() {
        value = null
    }

    fun getFirstStream() = value?.getFirstStreamFromList()
    fun getCategoriesAsString() = value?.categoriesToString()
    fun getStationName() = value?.name
    fun getStationImageUrl() = value?.image?.url
}