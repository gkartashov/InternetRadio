package com.jg.internetradio.repository

import android.content.Context
import com.jg.internetradio.R
import com.jg.internetradio.entity.Category
import com.jg.internetradio.entity.Station
import com.jg.internetradio.repository.remote.APIService

class RadioRepository private constructor() {
    companion object {
        val INSTANCE: RadioRepository by lazy { RadioRepository() }
    }

    lateinit var context: Context
    private val apiService = APIService.getInstance()

    fun getCategories() = RetrofitLiveData(apiService?.getCategories(context.resources.getString(R.string.apiKeyString))!!)

    fun getCategoryStations(category: Category) = RetrofitLiveData(apiService?.getCategoryStations(category.id, context.resources.getString(R.string.apiKeyString))!!)

    fun getStation(station: Station) = RetrofitLiveData(apiService?.getStation(context.resources.getString(R.string.apiKeyString), station.id)!!)
}