package com.jg.internetradio.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Station(@SerializedName("id") @Expose val id: Int = 0,
              @SerializedName("name") @Expose val name: String = "",
              @SerializedName("country") @Expose val country: String = "",
              @SerializedName("description") @Expose val description: String = "",
              @SerializedName("total_listeners") @Expose val totalListeners: Int = 0,
              @SerializedName("image") @Expose val image: Image? = null,
              @SerializedName("slug") @Expose val slug: String = "",
              @SerializedName("streams") @Expose val streams: List<Stream>? = null,
              @SerializedName("categories") @Expose val categories: List<Category>? = null)