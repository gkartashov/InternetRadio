package com.jg.internetradio.viewmodel

import android.app.Application
import android.arch.lifecycle.*
import com.jg.internetradio.InternetRadioApplication
import com.jg.internetradio.entity.Category
import com.jg.internetradio.entity.misc.capitalize
import com.jg.internetradio.repository.RadioRepository
import com.jg.internetradio.repository.RetrofitLiveData

class CategoryListViewModel(application: Application) : AndroidViewModel(application) {
    val isLoading = MutableLiveData<Boolean>()
    val isLoaded: Boolean
        get() = categoryList.isValueNotNull
    val categoryList: RetrofitLiveData<List<Category>>

    private val radioRepository: RadioRepository = (application as InternetRadioApplication).getRadioRepository()

    private val afterLoadAction: (List<Category>) -> Unit = { resultList ->
        isLoading.value = false
        resultList.map {
            it.description = capitalize(it.description)
        }
    }

    init {
        categoryList = radioRepository.getCategories()
        isLoading.value = true
        load()
    }

    private fun load() = categoryList.load(afterLoadAction, {})
}