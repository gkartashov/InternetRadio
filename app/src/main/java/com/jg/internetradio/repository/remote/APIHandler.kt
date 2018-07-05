package com.jg.internetradio.repository.remote

class APIHandler(private val apiService: APIService) {
    fun getCategories(token: String) = apiService.getCategories(token)
    fun getCategoryStations(token: String, categoryId: Int) = apiService.getCategoryStations(token, categoryId)
    fun getStation(token: String, stationId: Int) = apiService.getStation(token, stationId)
}