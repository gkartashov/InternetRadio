package com.jg.internetradio.repository

import android.arch.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class RetrofitLiveData<T>(private val call: Observable<T>?) : MutableLiveData<T>() {
    private var callResult: Disposable? = null

    fun load(onNextAction: (T) -> Unit, onErrorAction: () -> Unit) {
        callResult = call?.subscribeOn(Schedulers.io())
                ?.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({
                    postValue(it)
                    onNextAction.invoke(it)
                }, {
                    postValue(null)
                    onErrorAction.invoke()
                })
    }

    fun clear() {
        callResult?.dispose()
        value = null
    }

    override fun toString(): String = value.toString()
}