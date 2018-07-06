package com.jg.internetradio.viewmodel

import android.app.Application
import android.arch.lifecycle.*
import com.jg.internetradio.InternetRadioApplication
import com.jg.internetradio.R.string.apiKeyString
import com.jg.internetradio.entity.Category
import com.jg.internetradio.repository.RadioRepository
import com.jg.internetradio.repository.RetrofitLiveData


class CategoryListViewModel(application: Application) : AndroidViewModel(application) {
    private val radioRepository: RadioRepository = (application as InternetRadioApplication).getRadioRepository()
    var categoryList : RetrofitLiveData<MutableList<Category>>? = radioRepository.getCategories(getApplication<InternetRadioApplication>().applicationContext.resources.getString(apiKeyString))

    init {
        clear()
    }
    fun load() = categoryList?.load()

    fun clear() = categoryList?.clear()

    override fun toString() = categoryList.toString()

}