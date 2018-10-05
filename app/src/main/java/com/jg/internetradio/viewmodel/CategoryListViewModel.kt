package com.jg.internetradio.viewmodel

import android.app.Application
import android.arch.lifecycle.*
import com.jg.internetradio.InternetRadioApplication
import com.jg.internetradio.entity.Category
import com.jg.internetradio.repository.RadioRepository
import com.jg.internetradio.repository.RetrofitLiveData
import com.jg.internetradio.entity.misc.capitalize


class CategoryListViewModel(application: Application) : AndroidViewModel(application) {
    val isLoading = MutableLiveData<Boolean>()
    val isLoaded = MutableLiveData<Boolean>()
    val categoryList: RetrofitLiveData<List<Category>>

    private val radioRepository: RadioRepository = (application as InternetRadioApplication).getRadioRepository()

    private val afterLoadAction: (List<Category>) -> Unit = { loadResult ->
        isLoading.value = false
        loadResult.map { it.description = capitalize(it.description) }
    }

    private val onErrorAction = { isLoaded.value = false }

    init {
        categoryList = radioRepository.getCategories()
        isLoading.value = true
        isLoaded.value = true
        load()
    }

    private fun load() = categoryList.load(afterLoadAction, onErrorAction)
}