package com.jg.internetradio.repository

import android.arch.lifecycle.LiveData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitLiveData<T>(private val call: Call<T>?) : LiveData<T>(), Callback<T> {
    override fun onActive() = start()

    fun start() {
        if (call?.isCanceled == false && !call?.isExecuted)
            call.enqueue(this)
        else Unit
    }

    fun cancel() = if (call?.isCanceled == false) call.cancel() else Unit

    override fun onFailure(call: Call<T>?, t: Throwable?) {
    }

    override fun onResponse(call: Call<T>?, response: Response<T>?) {
        value = response?.body()

    }
    override fun toString(): String = value.toString()
}