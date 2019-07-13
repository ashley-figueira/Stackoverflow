package com.ashleyfigueira.stackoverflow.common

import android.view.View
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.ashleyfigueira.stackoverflow.GlideApp
import com.bumptech.glide.request.RequestOptions
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat

fun View.gone() { this.visibility = View.GONE }

fun View.visible() { this.visibility = View.VISIBLE }

fun DateTime.getFormattedDate(): String = DateTimeFormat.forPattern(DATE_FORMAT).print(this)

private const val DATE_FORMAT = "MM-dd-yyyy"

fun ImageView.load(url: String, options: RequestOptions = RequestOptions()) {
    GlideApp.with(this)
        .load(url)
        .apply(options)
        .into(this)
}
