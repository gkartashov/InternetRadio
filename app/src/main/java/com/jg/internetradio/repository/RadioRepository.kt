package com.jg.internetradio.repository

import com.jg.internetradio.entity.Category
import com.jg.internetradio.entity.Station
import com.jg.internetradio.repository.remote.APIService

class RadioRepository private constructor() {
    companion object {
        val INSTANCE: RadioRepository by lazy { RadioRepository() }
    }

    private val apiService = APIService.getInstance()

    fun getCategories(token: String) = RetrofitLiveData(apiService?.getCategories(token)!!)

    fun getCategoryStations(token: String, category: Category) = RetrofitLiveData(apiService?.getCategoryStations(token, category.id)!!)

    fun getStation(token: String, station: Station) = RetrofitLiveData(apiService?.getStation(token, station.id)!!)
}