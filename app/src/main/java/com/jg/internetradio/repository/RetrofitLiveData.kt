package com.jg.internetradio.repository

import android.arch.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class RetrofitLiveData<T>(private val call: Observable<T>?) : MutableLiveData<T>() {

    fun load(onNextAction: () -> Unit,
             onErrorAction: () -> Unit) {
        call
                ?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    postValue(it)
                    onNextAction.invoke()
                },
                {
                    postValue(null)
                    onErrorAction.invoke()
                })
    }

    fun clear() { value = null }

    override fun toString(): String = value.toString()
}