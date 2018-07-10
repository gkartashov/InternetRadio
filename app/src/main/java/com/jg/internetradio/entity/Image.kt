package com.jg.internetradio.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Image(@SerializedName("url") @Expose val url : String? = null,
                 @SerializedName("thumb") @Expose val thumb : Thumb? = null)