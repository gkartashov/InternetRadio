package com.jg.internetradio.repository.remote

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIController private constructor() {

    companion object {
        val INSTANCE: APIController by lazy { APIController() }
    }

    private val url = "http://api.dirble.com"
    val apiHandler: APIHandler

    init {
        val gson = GsonBuilder().create()
        val retrofit: Retrofit = Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        apiHandler = APIHandler(retrofit.create(APIService::class.java))
    }
}