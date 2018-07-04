package com.jg.internetradio.repository.remote

import com.jg.internetradio.entity.Category
import retrofit2.Call
import retrofit2.http.GET

interface APIService {
    @GET("v2/categories?token=e77cf64810184989ab4554d099")
    fun getCategories() : Call<MutableList<Category>>?
}