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

    val isLoading = MutableLiveData<Boolean>()
    val isLoaded: Boolean
        get() = stationList.isValueNotNull

    var stationList: RetrofitLiveData<List<Station>>

    private val afterLoadAction = { isLoading.value = false }

    private val radioRepository: RadioRepository = (application as InternetRadioApplication).getRadioRepository()

    init {
        stationList = radioRepository.getCategoryStations(category)
        isLoading.value = true
        load()
    }

    private fun load() = stationList.load(afterLoadAction)
}