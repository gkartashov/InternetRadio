package com.jg.internetradio.repository.remote

import com.jg.internetradio.entity.Category
import com.jg.internetradio.entity.Station
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface APIService {
    @GET("v2/categories" )
    fun getCategories(@Query("token") token: String) : Call<List<Category>>?

    @GET("v2/category/{categoryId}/stations")
    fun getCategoryStations(@Path("categoryId") categoryId : Int,
                            @Query("token") token: String) : Call<List<Station>>?

    @GET("v2/station/{station}")
    fun getStation(@Query("token") token: String,
                   @Path("station") stationId : Int) : Call<Station>?
}