package com.jg.internetradio.repository.remote

import com.jg.internetradio.entity.Category
import retrofit2.Call

class APIHandler(private val apiService: APIService) {
    fun getCategories() : Call<MutableList<Category>>? = apiService.getCategories()
}