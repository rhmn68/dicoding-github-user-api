package com.example.aplikasigithubuser.common.utils

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import com.bumptech.glide.Glide

/**
 * IMAGE GLIDE
 */

fun View.inputImageGlide(resourceId: String?, imageView: ImageView) {
  Glide.with(this)
    .load(resourceId)
    .placeholder(android.R.color.darker_gray)
    .centerCrop()
    .into(imageView)
}

fun View.inputImageGlide(@RawRes @DrawableRes resourceId: Int?, imageView: ImageView) {
  Glide.with(this)
    .load(resourceId)
    .placeholder(android.R.color.darker_gray)
    .into(imageView)
}

fun Context.inputImageGlide(@RawRes @DrawableRes resourceId: Int?, imageView: ImageView) {
  Glide.with(this)
    .load(resourceId)
    .placeholder(android.R.color.darker_gray)
    .into(imageView)
}

fun Context.inputImageGlide(resourceId: String?, imageView: ImageView) {
  Glide.with(this)
    .load(resourceId)
    .placeholder(android.R.color.darker_gray)
    .into(imageView)
}