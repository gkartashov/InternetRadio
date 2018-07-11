package com.jg.internetradio.ui.misc

import android.graphics.drawable.Drawable
import com.bumptech.glide.request.RequestOptions
import com.jg.internetradio.R

fun getRequestOptions(placeholder: Drawable?): RequestOptions =
    RequestOptions().placeholder(placeholder).fallback(R.drawable.ic_note)