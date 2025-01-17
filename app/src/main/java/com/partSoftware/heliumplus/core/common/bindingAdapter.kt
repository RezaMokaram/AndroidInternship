package com.partSoftware.heliumplus.core.common

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.google.android.material.imageview.ShapeableImageView
import com.partSoftware.heliumplus.R

@BindingAdapter("imageSrc")
fun ShapeableImageView.loadPicture(url: String?) {
    if (url == null)
        this.load(R.drawable.img_sample_2) {
            crossfade(true)
            crossfade(500)
        }
    else
        this.load(url)
}

@BindingAdapter("setVisibility")
fun View.setVisibility(isVisible: Boolean) {
    visibility = if (isVisible)
        View.VISIBLE
    else
        View.INVISIBLE
}

@BindingAdapter("setVisibilityGone")
fun View.setVisibleOrGone(isVisible: Boolean) {
    visibility = if (isVisible)
        View.VISIBLE
    else
        View.GONE
}

@BindingAdapter("isBookmarked")
fun ImageView.isBookmarked(isBookmarked: Boolean) {
    if (isBookmarked)
        setBackgroundResource(R.drawable.ic_bookmarked)
    else
        setBackgroundResource(R.drawable.ic_add_bookmark)
}