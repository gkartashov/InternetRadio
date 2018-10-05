package com.jg.internetradio.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Stream(@SerializedName("stream") @Expose val stream: String? = null,
                  @SerializedName("bitrate") @Expose val bitrate: Int = 0,
                  @SerializedName("content_type") @Expose val contentType: String? = "",
                  @SerializedName("listeners") @Expose val listeners: Int = 0,
                  @SerializedName("status") @Expose val status: Int = 0)