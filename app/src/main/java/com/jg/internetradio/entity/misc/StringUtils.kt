package com.jg.internetradio.entity.misc

import com.jg.internetradio.entity.Category

fun capitalize(input: String) = input.split('.')
        .joinToString(". ") { it.trim().capitalize() }

fun categoryListToString(categoryList: List<Category>?): String {
    return categoryList?.joinToString(", ") { it.title } ?: ""
}

