package com.jg.internetradio.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Category(@SerializedName("id") @Expose val id: Int = 0,
                    @SerializedName("title") @Expose val title: String = "",
                    @SerializedName("description") @Expose val description: String = "",
                    @SerializedName("slug") @Expose val slug: String = "") : Serializable {

    fun capitalize(input: String) = input.split('.').joinToString(". ") { it.trim().capitalize()}
}
