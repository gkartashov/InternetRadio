package com.jg.internetradio.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Image(@SerializedName("image") @Expose val image: InnerImage? = null)

