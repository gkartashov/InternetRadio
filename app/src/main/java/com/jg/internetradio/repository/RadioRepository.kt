package com.jg.internetradio.repository

import com.jg.internetradio.entity.Category
import com.jg.internetradio.entity.Station
import com.jg.internetradio.repository.remote.APIController

class RadioRepository private constructor() {
    companion object {
        val INSTANCE: RadioRepository by lazy { RadioRepository() }
    }

    private val apiController = APIController.INSTANCE

    fun getCategories(token: String) = RetrofitLiveData(apiController.apiHandler.getCategories(token)!!)

    fun getCategoryStations(token: String, category: Category) = RetrofitLiveData(apiController.apiHandler.getCategoryStations(token, category.id)!!)

    fun getStation(token: String, station: Station) = RetrofitLiveData(apiController.apiHandler.getStation(token, station.id)!!)
}