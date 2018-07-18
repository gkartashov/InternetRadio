package com.jg.internetradio.repository.remote

import com.google.gson.GsonBuilder
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object APIHandler {
    private const val url = "http://api.dirble.com"

    private val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()

    val INSTANCE: APIService? = retrofit.create(APIService::class.java)
}