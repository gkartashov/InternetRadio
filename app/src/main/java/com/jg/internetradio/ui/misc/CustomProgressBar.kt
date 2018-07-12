package com.jg.internetradio.ui.misc

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v4.widget.CircularProgressDrawable
import com.jg.internetradio.R

fun circularProgressDrawable(context: Context) = CircularProgressDrawable(context).apply { centerRadius = 20f
    strokeWidth = 4f
    setColorSchemeColors(ContextCompat.getColor(context, R.color.colorAccent))
    start()
}
