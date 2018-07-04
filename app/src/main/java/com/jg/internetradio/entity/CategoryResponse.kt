package com.jg.internetradio.entity

class CategoryResponse(val categories : MutableList<Category>) {
    enum class SortType { NAME, ID }

    fun sortBy(sortType: SortType) = when(sortType) {
            SortType.NAME -> categories.sortWith(compareBy(Category::title))
            SortType.ID -> categories.sortWith(compareBy(Category::id))
    }

    fun find(categoryTitle: String) = categories.find { it.title == categoryTitle }
}


