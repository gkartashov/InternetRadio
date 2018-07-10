package com.jg.internetradio.repository.remote

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIHandler {
    private const val url = "http://api.dirble.com"

    private val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()

    val INSTANCE: APIService? = retrofit.create(APIService::class.java)
}