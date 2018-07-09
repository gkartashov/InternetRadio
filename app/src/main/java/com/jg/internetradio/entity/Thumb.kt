package com.jg.internetradio.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Thumb(@SerializedName("url") @Expose val url: String? = null)