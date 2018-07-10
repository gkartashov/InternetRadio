package com.jg.internetradio.viewmodel

import android.app.Application
import android.arch.lifecycle.*
import com.jg.internetradio.InternetRadioApplication
import com.jg.internetradio.entity.Category
import com.jg.internetradio.entity.Station
import com.jg.internetradio.repository.RadioRepository
import com.jg.internetradio.repository.RetrofitLiveData


class StationListViewModel(application: Application, val category: Category) : AndroidViewModel(application) {

    class Factory(private val application: Application, private val category: Category) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return StationListViewModel(application, category) as T
        }
    }

    private val radioRepository: RadioRepository = (application as InternetRadioApplication).getRadioRepository()
    var stationList : RetrofitLiveData<List<Station>>? = radioRepository.getCategoryStations(category)

    init {
        load()
    }

    fun load() = stationList?.load()

    fun clear() = stationList?.clear()

    override fun toString() = stationList.toString()

}