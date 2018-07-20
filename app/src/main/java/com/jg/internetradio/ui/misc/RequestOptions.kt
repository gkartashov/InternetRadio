package com.jg.internetradio.ui.misc

import android.graphics.drawable.Drawable
import com.bumptech.glide.request.RequestOptions

fun getRequestOptions(placeholder: Drawable?, error: Drawable?): RequestOptions =
    RequestOptions().placeholder(placeholder).fallback(error)