package com.jg.internetradio.binding

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.jg.internetradio.ui.misc.circularProgressDrawable
import com.jg.internetradio.ui.misc.getRequestOptions


@BindingAdapter("isVisible")
fun visibilitySetter(view: View, show: Boolean) {
    view.visibility = if (show) View.VISIBLE else View.GONE
}

@BindingAdapter("isActivated")
fun activationSetter(view: View, show: Boolean) {
    view.isActivated = show
}

@BindingAdapter("imageUrl", "errorDrawable")
fun loadImage(view: ImageView, url: String?, error: Drawable) {
    if (url.isNullOrEmpty())
        view.setImageDrawable(error)
    else
        Glide
                .with(view.context)
                .setDefaultRequestOptions(getRequestOptions(circularProgressDrawable(view.context), error))
                .load(url?.trim())
                .into(view)
}