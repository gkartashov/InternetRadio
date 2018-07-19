package com.jg.internetradio.binding

import android.arch.lifecycle.MutableLiveData
import android.databinding.BindingAdapter
import android.databinding.BindingConversion
import android.view.View

@BindingAdapter("app:isVisible")
fun visibilitySetter(view: View, show: Boolean) {
    view.visibility = if (show) View.VISIBLE else View.GONE
}

@BindingAdapter("app:isActivated")
fun activationSetter(view: View, show: Boolean) {
    view.isActivated = show
}

@BindingConversion
fun <T> getMutableLiveDataValue(liveData: MutableLiveData<T>): T {
    return liveData.value ?: throw IllegalArgumentException("liveData's value shouldn't be null!")
}