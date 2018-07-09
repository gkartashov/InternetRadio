package com.jg.internetradio.repository.remote

import com.google.gson.GsonBuilder
import com.jg.internetradio.entity.Category
import com.jg.internetradio.entity.Station
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

    companion object {
        private var instance: APIService? = null

        private const val url = "http://api.dirble.com"

        private val retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
                .build()

        fun getInstance(): APIService? {
            if (instance == null)
                instance = retrofit.create(APIService::class.java)
            return instance
        }
    }
}