package com.jg.internetradio.repository

import android.arch.lifecycle.LiveData

class PlayerLiveData<Station> : LiveData<Station>() {
    fun addDataSource(dataSource: Station) { value = dataSource }
    fun removeDataSource() { value = null }
}