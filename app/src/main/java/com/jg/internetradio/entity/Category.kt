package com.jg.internetradio.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.jg.internetradio.entity.misc.capitalize
import java.io.Serializable

data class Category(@SerializedName("id") @Expose val id: Int = 0,
                    @SerializedName("title") @Expose val title: String = "",
                    @SerializedName("slug") @Expose val slug: String = "") : Serializable {
    @SerializedName("description")
    @Expose
    var description: String = ""

    constructor(id: Int, title: String = "", description: String = "", slug: String = "") : this(id, title, slug) {
        this.description = capitalize(description)
    }
}